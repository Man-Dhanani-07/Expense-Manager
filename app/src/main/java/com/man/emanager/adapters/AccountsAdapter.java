package com.man.emanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.man.emanager.R;
import com.man.emanager.databinding.RawAccountBinding;
import com.man.emanager.model.Account;
import com.man.emanager.model.Category;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>{

    Context context;
    ArrayList<Account> accountArrayList;

    public interface AccountsClickListner{
        void onAccountSelected(Account account);
    }

    AccountsClickListner accountsClickListner;
    public AccountsAdapter(Context context, ArrayList<Account>accountArrayList,AccountsClickListner accountsClickListner){
        this.context = context;
        this.accountArrayList = accountArrayList;
        this.accountsClickListner = accountsClickListner;
    }
    public AccountsAdapter(Context context, ArrayList<Account>accountArrayList){
        this.context = context;
        this.accountArrayList= accountArrayList;
    }
    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountsViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_account,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        Account account = accountArrayList.get(position);
        holder.binding.accountName.setText(account.getAccountName());
        holder.binding.accounticon.setImageResource(account.getAccountImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountsClickListner.onAccountSelected(account);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountsViewHolder extends RecyclerView.ViewHolder{
        RawAccountBinding binding;
        public AccountsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RawAccountBinding.bind(itemView);
        }
    }
}
