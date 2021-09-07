package com.example.apersonalfinancialmanager;

public class ViewSavigsList {

    private String amount;
    private String date;
    private String category;
    private String deposit;
    private String withdrawal;


    public ViewSavigsList()
    {
    }

    public ViewSavigsList(String amount,String category, String date, String deposit, String withdrawal) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.deposit = deposit;
        this.withdrawal = withdrawal;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(String withdrawal) {
        this.withdrawal = withdrawal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
