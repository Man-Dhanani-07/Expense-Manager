package com.man.emanager.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.man.emanager.R;
import com.man.emanager.adapters.AccountsAdapter;
import com.man.emanager.adapters.CategoryAdapter;
import com.man.emanager.databinding.FragmentAddTransaction2Binding;
import com.man.emanager.databinding.ListDialogBinding;
import com.man.emanager.model.Account;
import com.man.emanager.model.Category;
import com.man.emanager.model.Transaction;
import com.man.emanager.utils.Constants;
import com.man.emanager.utils.Helper;
import com.man.emanager.viewmodels.MainViewModel;
import com.man.emanager.views.activites.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactionFragment extends BottomSheetDialogFragment {

    FragmentAddTransaction2Binding binding;
    Transaction transaction;
    public AddTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTransaction2Binding.inflate(inflater);

        transaction = new Transaction();


        binding.incomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expensebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expensebtn.setBackground(getContext().getDrawable(R.color.textcolor));
                binding.incomebtn.setTextColor(getContext().getColor(R.color.Green));

                transaction.setType("Income");
            }
        });

        binding.expensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expensebtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.incomebtn.setBackground(getContext().getDrawable(R.color.textcolor));
                binding.expensebtn.setTextColor(getContext().getColor(R.color.Red));

                transaction.setType("Expense");
            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog  = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener((DatePicker,i,i1,i2)->{
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, DatePicker.getDayOfMonth());
                    calendar.set(Calendar.MONTH,DatePicker.getMonth());
                    calendar.set(Calendar.YEAR,DatePicker.getYear());



//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, YYYY");
//                    String datetoshow = dateFormat.format(calendar.getTime());

                    String datetoshow = Helper.formateDate(calendar.getTime());
                    binding.date.setText(datetoshow);
                    transaction.setDate(calendar.getTime());
                    transaction.setId(calendar.getTime().getTime());
                });
                datePickerDialog.show();
            }
        });

        binding.category.setOnClickListener(c-> {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
                AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
                categoryDialog.setView(dialogBinding.getRoot());

                // set recycler view


                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickListner() {
                    @Override
                    public void onCategoryClicked(Category category) {
                        binding.category.setText(category.getCategoryName());
                        transaction.setCategory(category.getCategoryName());
                        categoryDialog.dismiss();
                    }
                });
                dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                dialogBinding.recyclerView.setAdapter(categoryAdapter);
                categoryDialog.show();

        });

        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
                AlertDialog accountDialog = new AlertDialog.Builder(getContext()).create();
                accountDialog.setView(dialogBinding.getRoot());

                ArrayList<Account> accounts = new ArrayList<>();

                accounts.add(new Account(0,"Cash",R.drawable.cash));
                accounts.add(new Account(0,"Gpay",R.drawable.gpay));
                accounts.add(new Account(0,"PhonePe",R.drawable.phone_pe));
                accounts.add(new Account(0,"Paytm",R.drawable.paytm));
                accounts.add(new Account(0,"Card Payment",R.drawable.card_payment));
                accounts.add(new Account(0,"Mobile Banking",R.drawable.mobile_banking));
                accounts.add(new Account(0,"PayPal", R.drawable.paypal));
                accounts.add(new Account(0,"Other",R.drawable.other_payment));

                AccountsAdapter adapter = new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountsClickListner() {
                    @Override
                    public void onAccountSelected(Account account) {
                        binding.account.setText(account.getAccountName());
                        transaction.setAccount(account.getAccountName());
                        accountDialog.dismiss();
                    }
                });
                dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                dialogBinding.recyclerView.setAdapter(adapter);

                accountDialog.show();

            }
        });

        binding.savetransactionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(binding.amount.getText().toString());
//                String note = binding.note.getText().toString();

                if(transaction.getType().equals("Expense")){
                    transaction.setAmount(amount*-1);
                }
                else{
                    transaction.setAmount(amount);
                }
//                transaction.setNote(note);
                ((MainActivity)getActivity()).viewModel.addTransactions(transaction);
                ((MainActivity)getActivity()).getTransaction();
                dismiss();
            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}