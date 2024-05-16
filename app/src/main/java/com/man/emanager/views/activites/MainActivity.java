package com.man.emanager.views.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.man.emanager.utils.Constants;
import com.man.emanager.viewmodels.MainViewModel;
import com.man.emanager.R;
import com.man.emanager.databinding.ActivityMainBinding;
import com.man.emanager.views.fragments.StatsFragment;
import com.man.emanager.views.fragments.TransactionsFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Calendar calendar;

    // 0 = daily
    // 1 = monthly
    // 2 = calendar;
    // 3 = summary
    // 4 = notes
   public MainViewModel viewModel;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            viewModel = new ViewModelProvider(this).get(MainViewModel.class);

//            setSupportActionBar(binding.content);
            setSupportActionBar(binding.notes);
            getSupportActionBar().setTitle("Transactions");

            Constants.setCategories();
            calendar = Calendar.getInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content,new TransactionsFragment());
            transaction.commit();
            binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    if(item.getItemId()==R.id.transactions){
                        getSupportFragmentManager().popBackStack();
                    }
                    else if(item.getItemId()==R.id.Stats){
                        transaction.replace(R.id.content,new StatsFragment());
                        transaction.addToBackStack(null);
                        getSupportFragmentManager().popBackStack();
                    }
                    transaction.commit();
                    return true;
                }
            });
        }
    public void getTransaction(){
            viewModel.getTransactions(calendar);
        }
    // update date function thi current date set thay jay


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}