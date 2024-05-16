package com.man.emanager.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.man.emanager.model.Transaction;
import com.man.emanager.utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {
   public MutableLiveData<RealmResults<Transaction>> transactions = new MutableLiveData<>();
    public MutableLiveData<RealmResults<Transaction>> categoriesTransactions = new MutableLiveData<>();
   public MutableLiveData<Double> totalIncome =  new MutableLiveData<>();
   public MutableLiveData<Double> totalexpense = new MutableLiveData<>();
   public MutableLiveData<Double> totalAmount = new MutableLiveData<>();
    Realm realm;
    Calendar calendar;
    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setupDatabase();
    }
    public void getTransactions(Calendar calendar, String type){
        this.calendar = calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        RealmResults<Transaction> newTransactions = null;
        if(Constants.SELECTED_TAB_STATS==Constants.DAILY){


            newTransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type",type)
                    .findAll();
        }
        else if (Constants.SELECTED_TAB_STATS ==Constants.MONTHLY){
            calendar.set(Calendar.DAY_OF_MONTH,0);

            Date starTtime = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime();
            newTransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", starTtime)
                    .lessThan("date", endTime)
                    .equalTo("type",type)
                    .findAll();
        }
        categoriesTransactions.setValue(newTransactions);
    }
    public void getTransactions(Calendar calendar){
        this.calendar = calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        double income = 0;
        double expense = 0;
        double total =0;
        RealmResults<Transaction> newTransactions = null;
        if(Constants.SELECTED_TAB==Constants.DAILY){

            // select*from transactions
            // select*from transaction where id = 4;
//        RealmResults<Transaction> newTransactions = realm.where(Transaction.class)
//                .equalTo("date", calendar.getTime())
//                .findAll();

          newTransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .findAll();
             income = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", "Income")
                    .sum("amount")
                    .doubleValue();
             expense = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", "Expense")
                    .sum("amount")
                    .doubleValue();
             total = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", calendar.getTime())
                    .lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .sum("amount")
                    .doubleValue();
        }
        else if (Constants.SELECTED_TAB ==Constants.MONTHLY){
            calendar.set(Calendar.DAY_OF_MONTH,0);

            Date starTtime = calendar.getTime();
            calendar.add(Calendar.MONTH,1);
            Date endTime = calendar.getTime();
           newTransactions = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", starTtime)
                    .lessThan("date", endTime)
                    .findAll();
            income = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", starTtime)
                    .lessThan("date", endTime)
                    .equalTo("type", "Income")
                    .sum("amount")
                    .doubleValue();
            expense = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", starTtime)
                    .lessThan("date", endTime)
                    .equalTo("type", "Expense")
                    .sum("amount")
                    .doubleValue();
            total = realm.where(Transaction.class)
                    .greaterThanOrEqualTo("date", starTtime)
                    .lessThan("date", endTime)
                    .sum("amount")
                    .doubleValue();
        }

        totalIncome.setValue(income);
        totalexpense.setValue(expense);
        totalAmount.setValue(total);
        transactions.setValue(newTransactions);

    }
    public void addTransactions(Transaction transaction){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(transaction);
        realm.commitTransaction();
    }

    public void deleteTransaction(Transaction transaction){
        realm.beginTransaction();
        transaction.deleteFromRealm();
        realm.commitTransaction();
        getTransactions(calendar);
    }
//    public void addTransactions(){
//        realm.beginTransaction();
//        realm.copyToRealmOrUpdate(new Transaction("Income","Business","Cash","Some Note here", new Date(), 500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction("Expense","Social","Gpay","Some Note here", new Date(), -50140,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction("Expense","Shopping","Paytm","Some Note here", new Date(), -5040,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction("Income","Pet","Card Payment","Some Note here", new Date(), 14500,new Date().getTime()));
//        realm.copyToRealmOrUpdate(new Transaction("Expense","Loan","PayPal","Some Note here", new Date(), 0,new Date().getTime()));
//
//        realm.commitTransaction();
//    }
    void setupDatabase(){
        realm = Realm.getDefaultInstance();
    }
}



// MUTABLE OBJECTS AND IMMUTABLE OBJECTS
// mutable objects are those to change their  value or data without affecting the object identity.
// immutable dont allow this kind of opration