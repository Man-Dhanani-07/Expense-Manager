package com.man.emanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.man.emanager.R;
import com.man.emanager.databinding.RawAccountBinding;
import com.man.emanager.databinding.RawTransactionsBinding;
import com.man.emanager.model.Account;
import com.man.emanager.model.Category;
import com.man.emanager.model.Transaction;
import com.man.emanager.utils.Constants;
import com.man.emanager.utils.Helper;
import com.man.emanager.views.activites.MainActivity;

import java.util.ArrayList;

import io.realm.RealmResults;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

    Context context;
    RealmResults<Transaction> transactions;

    public TransactionsAdapter(Context context, RealmResults<Transaction> transactions){
        this.context = context;
        this.transactions = transactions;
    }
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_transactions,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountLbl.setText(transaction.getAccount());
        holder.binding.transactionDate.setText(Helper.formateDate(transaction.getDate()));
        holder.binding.transactionCategory.setText(transaction.getCategory());



        Category transactionCategory = Constants.getCategoryDetails(transaction.getCategory());
        holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryimage());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(transactionCategory.getCategoryColor()));

        holder.binding.accountLbl.setBackgroundTintList(context.getColorStateList(Constants.getAccountColor(transaction.getAccount())));


//        Account accountCategory  = Constants.getAccountDetails(transaction.getAccount());
//        holder.binding1.accounticon.setImageResource(accountCategory.getAccountImage());

        if (transaction.getType().equals("Income")){
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.Greenn));
        }
        else if (transaction.getType().equals("Expense")){
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.Redd));
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Delete Transaction");
                deleteDialog.setMessage("Are you sure to delete this transactions");
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)context).viewModel.deleteTransaction(transaction);
                    }
                });
                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        RawTransactionsBinding binding;
//        RawAccountBinding binding1;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RawTransactionsBinding.bind(itemView);
//            binding1 = RawAccountBinding.bind(itemView);
        }
    }
}
