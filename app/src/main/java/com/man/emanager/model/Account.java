package com.man.emanager.model;

public class Account {
    private double accountAmount;
    private String accountName;
    private int accountImage;

    public Account(){

    }
    public Account(double accountAmount, String accountName,int accountImage) {
        this.accountAmount = accountAmount;
        this.accountName = accountName;
        this.accountImage = accountImage;
    }

    public int getAccountImage() {
        return accountImage;
    }

    public void setAccountImage(int accountImage) {
        this.accountImage = accountImage;
    }

    public double getAccountAmount() {

        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {

        this.accountAmount = accountAmount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


}
