package com.man.emanager.views.fragments;

import static com.man.emanager.utils.Constants.SELECTED_STATS_TYPE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.android.material.tabs.TabLayout;
import com.man.emanager.R;
import com.man.emanager.databinding.FragmentStatsBinding;
import com.man.emanager.model.Transaction;
import com.man.emanager.utils.Constants;
import com.man.emanager.utils.Helper;
import com.man.emanager.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;

public class StatsFragment extends Fragment {



    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentStatsBinding binding;
    Calendar calendar;
    public MainViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatsBinding.inflate(inflater);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar = Calendar.getInstance();
        updatedate();

        binding.incomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expensebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expensebtn.setBackground(getContext().getDrawable(R.color.textcolor));
                binding.incomebtn.setTextColor(getContext().getColor(R.color.Green));

                SELECTED_STATS_TYPE = "Income";
                updatedate();
            }
        });

        binding.expensebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomebtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expensebtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.incomebtn.setBackground(getContext().getDrawable(R.color.textcolor));
                binding.expensebtn.setTextColor(getContext().getColor(R.color.Red));

              SELECTED_STATS_TYPE = "Expense";
              updatedate();
            }
        });

        binding.nextdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                    calendar.add(Calendar.DATE, 1);
                }
                else if(Constants.SELECTED_TAB_STATS == Constants.MONTHLY){
                    calendar.add(Calendar.MONTH, 1);
                }
                updatedate();
            }
        });
        binding.previousdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SELECTED_TAB_STATS == Constants.DAILY) {
                    calendar.add(Calendar.DATE, -1);
                }
                else if(Constants.SELECTED_TAB_STATS == Constants.MONTHLY){
                    calendar.add(Calendar.MONTH,-1);
                }
                updatedate();
            }
        });

        binding.tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Monthly")){
                    Constants.SELECTED_TAB_STATS = 1;
                    updatedate();
                }
                else if(tab.getText().equals("Daily")){
                    Constants.SELECTED_TAB_STATS = 0;
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


        Pie pie = AnyChart.pie();
        viewModel.categoriesTransactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {


                if (transactions.size()>0){
                    binding.emptyStates.setVisibility(View.GONE);
                    binding.anyChart.setVisibility(View.VISIBLE);
                    List<DataEntry> data = new ArrayList<>();
                    Map<String,Double> categoryMap = new HashMap<>();
                    for (Transaction transaction:transactions){
                        String category = transaction.getCategory();
                        double amount = transaction.getAmount();

                        if(categoryMap.containsKey(category)){
                            double currentTotal = categoryMap.get(category).doubleValue();
                            currentTotal+= Math.abs(amount);
                            categoryMap.put(category,currentTotal);
                        }
                        else{
                            categoryMap.put(category,Math.abs(amount));
                        }
                    }
                    for (Map.Entry<String,Double>entry:categoryMap.entrySet()){
                        data.add(new ValueDataEntry(entry.getKey(),entry.getValue()));
                    }
                    pie.data(data);

                }
                else{
                    binding.emptyStates.setVisibility(View.VISIBLE);
                    binding.anyChart.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getTransactions(calendar,SELECTED_STATS_TYPE);
//        pie.title("Fruits imported in 2015 (in kg)");
//
//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Retail channels")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);

        binding.anyChart.setChart(pie);
        return binding.getRoot();
    }
    void updatedate(){
        if(Constants.SELECTED_TAB_STATS == Constants.DAILY) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, YYYY");
//        binding.currentDate.setText(dateFormat.format(calendar.getTime()));
            // aa helper class ma alag thi function banavine date formate karavi chhe etle upar ni be line nahi
            // to nicheni ek line lakhvi to chale
            binding.currentDate.setText(Helper.formateDate(calendar.getTime()));
        }else if(Constants.SELECTED_TAB_STATS == Constants.MONTHLY){
            binding.currentDate.setText(Helper.formateDatebymonth(calendar.getTime()));
        }
        viewModel.getTransactions(calendar,SELECTED_STATS_TYPE);
    }
}