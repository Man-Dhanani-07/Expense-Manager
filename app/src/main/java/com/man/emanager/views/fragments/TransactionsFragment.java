package com.man.emanager.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.man.emanager.R;
import com.man.emanager.adapters.TransactionsAdapter;
import com.man.emanager.databinding.FragmentTransactionsBinding;
import com.man.emanager.model.Transaction;
import com.man.emanager.utils.Constants;
import com.man.emanager.utils.Helper;
import com.man.emanager.viewmodels.MainViewModel;
import com.man.emanager.views.activites.MainActivity;

import java.util.Calendar;

import io.realm.RealmResults;


public class TransactionsFragment extends Fragment {


    public TransactionsFragment() {
        // Required empty public constructor
    }
    FragmentTransactionsBinding binding;
    Calendar calendar;

    // 0 = daily
    // 1 = monthly
    // 2 = calendar;
    // 3 = summary
    // 4 = notes
    public MainViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar = Calendar.getInstance();
        updatedate();

        binding.nextdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SELECTED_TAB==Constants.DAILY) {
                    calendar.add(Calendar.DATE, 1);
                }
                else if(Constants.SELECTED_TAB==Constants.MONTHLY){
                    calendar.add(Calendar.MONTH, 1);
                }
                updatedate();
            }
        });
        binding.previousdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SELECTED_TAB == Constants.DAILY) {
                    calendar.add(Calendar.DATE, -1);
                }
                else if(Constants.SELECTED_TAB == Constants.MONTHLY){
                    calendar.add(Calendar.MONTH,-1);
                }
                updatedate();
            }
        });
        binding.floatingActionButtontransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddTransactionFragment().show(getParentFragmentManager(),null);
            }
        });

        binding.tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB = 1;
                    updatedate();
                }
                else if(tab.getText().equals("Daily")){
                    Constants.SELECTED_TAB = 0;
                    updatedate();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.transactionlist.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.transactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionsAdapter transactionsAdapter = new TransactionsAdapter(getActivity(), transactions);
                binding.transactionlist.setAdapter(transactionsAdapter);
                if(transactions.size()>0){
                    binding.emptystates.setVisibility(View.GONE);
                }else{
                    binding.emptystates.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.totalIncome.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeLbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.totalexpense.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseLbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.totalAmount.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalLbl.setText(String.valueOf(aDouble));
            }
        });
        viewModel.getTransactions(calendar);

        return binding.getRoot();
    }
    void updatedate(){
        if(Constants.SELECTED_TAB  == Constants.DAILY) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, YYYY");
//        binding.currentDate.setText(dateFormat.format(calendar.getTime()));
            // aa helper class ma alag thi function banavine date formate karavi chhe etle upar ni be line nahi
            // to nicheni ek line lakhvi to chale
            binding.currentDate.setText(Helper.formateDate(calendar.getTime()));
        }else if(Constants.SELECTED_TAB == Constants.MONTHLY){
            binding.currentDate.setText(Helper.formateDatebymonth(calendar.getTime()));
        }
        viewModel.getTransactions(calendar);
    }
}