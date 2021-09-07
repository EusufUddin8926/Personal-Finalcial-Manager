package com.example.apersonalfinancialmanager;

public class ViewReportRevenueCostList{

    private String category;
    private String amount;
    private String date;
    private String revenue;
    private String expense;


    public ViewReportRevenueCostList()
    {

    }

    public ViewReportRevenueCostList(String category, String amount, String date, String revenue, String expense) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.revenue = revenue;
        this.expense = expense;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }
}
