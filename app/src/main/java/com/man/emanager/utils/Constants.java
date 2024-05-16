package com.man.emanager.utils;

import com.man.emanager.R;
import com.man.emanager.model.Account;
import com.man.emanager.model.Category;

import java.util.ArrayList;

public class Constants {

//    public static String INCOME = "INCOME";
//    public static String EXPENSE = "EXPENSE";
    public static ArrayList<Category> categories;

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDAR = 2;
    public static int SUMMARY = 3;
    public static int NOTES = 4;
    public static int SELECTED_TAB = 0;
    public static int SELECTED_TAB_STATS = 0;
    public static String SELECTED_STATS_TYPE = "Income";

    public static ArrayList<Account> accounts;
    public static void setCategories(){
        categories = new ArrayList<>();
        categories.add(new Category("Salary", R.drawable.salary, R.color.catedarkblue));
        categories.add(new Category("Shopping", R.drawable.shopping1, R.color.categorybrown));
        categories.add(new Category("Food", R.drawable.fast_food, R.color.categoryorange));
        categories.add(new Category("Telephone", R.drawable.telephone1, R.color.categorypink));
        categories.add(new Category("Entertainment", R.drawable.entertainment, R.color.catelightgreen));
        categories.add(new Category("Beauty", R.drawable.cosmetics, R.color.catelightblue));
        categories.add(new Category("Sports", R.drawable.football, R.color.categorybrown));
        categories.add(new Category("Social", R.drawable.social,R.color.Red));
        categories.add(new Category("Transportation", R.drawable.school_bus, R.color.categorypurple));
        categories.add(new Category("Liquor", R.drawable.alcoholic_drink, R.color.catelightblue));
        categories.add(new Category("Clothing", R.drawable.shirt, R.color.categoryorange));
        categories.add(new Category("Car", R.drawable.electric_car, R.color.catelightgreen));
        categories.add(new Category("Cigarette", R.drawable.cigarette, R.color.catedarkblue));
        categories.add(new Category("Electronics", R.drawable.responsive_design, R.color.categorypurple));
        categories.add(new Category("Travel", R.drawable.travel, R.color.categorybrown));
        categories.add(new Category("Repair", R.drawable.repair, R.color.categorypink));
        categories.add(new Category("Health", R.drawable.healthcare,  R.color.catelightblue));
        categories.add(new Category("Pet", R.drawable.pets, R.color.catedarkblue));
        categories.add(new Category("Home", R.drawable.home, R.color.catelightgreen));
        categories.add(new Category("Business", R.drawable.handshake, R.color.categorypink));
        categories.add(new Category("Gift", R.drawable.gift, R.color.categorybrown));
        categories.add(new Category("Child", R.drawable.child, R.color.categorypurple));
        categories.add(new Category("Donate", R.drawable.donate, R.color.Red));
        categories.add(new Category("Snacks", R.drawable.food1,  R.color.catelightblue));
        categories.add(new Category("Vegetables", R.drawable.vegetables, R.color.catedarkblue));
        categories.add(new Category("Fruits", R.drawable.healthy_food, R.color.categorybrown));
        categories.add(new Category("Other", R.drawable.other, R.color.categoryorange));
        categories.add(new Category("Investment", R.drawable.profits, R.color.catelightgreen));
        categories.add(new Category("Loan",R.drawable.money,R.color.categorypurple));
    }



    public static Category getCategoryDetails(String categoryName){
        for (Category cat :
                categories) {
            if(cat.getCategoryName().equals(categoryName))
            {
                return cat;
            }
            
        }
        return null;
    }

    public static int getAccountColor(String accountname){
        switch (accountname){
            case "Gpay":
                return R.color.Gpay;
                case "Cash":
                    return R.color.Cash;
                case "PhonePe":
                    return R.color.phonepe;
                case "Paytm":
                    return R.color.paytm;
                case "Card Payment":
                    return R.color.Cash;
                case "Mobile Banking":
                    return R.color.mobilebanking;
                case "PayPal":
                    return R.color.phonepe;
            default:
                return R.color.other;
        }
    }

}
