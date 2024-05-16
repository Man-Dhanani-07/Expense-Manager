package com.man.emanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.man.emanager.R;
import com.man.emanager.databinding.SampleCategoryItemBinding;
import com.man.emanager.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    ArrayList<Category>categories;

    public interface CategoryClickListner{
        void onCategoryClicked(Category category);
    }

    CategoryClickListner categoryClickListner;

  public CategoryAdapter(Context context,ArrayList<Category>categories, CategoryClickListner categoryClickListner){
        this.context = context;
        this.categories = categories;
        this.categoryClickListner = categoryClickListner;
  }
    public CategoryAdapter(Context context, ArrayList<Category>categories){
       this.context = context;
       this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categories.get(position);
        holder.binding.categoryText.setText(category.getCategoryName());
        holder.binding.categoryicon.setImageResource(category.getCategoryimage());
        holder.binding.categoryicon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickListner.onCategoryClicked(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        SampleCategoryItemBinding binding;
        public CategoryViewHolder(View itemView){
        // bind tyare use karvanu jyare apdi pase view available hoy binding mate
            // layout inflalor tyare use karvanu jyare apdi pase layoutinflator hoy
            super(itemView);
            binding = SampleCategoryItemBinding.bind(itemView);
        }
    }
}
