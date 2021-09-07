package com.example.apersonalfinancialmanager;

import com.google.firebase.database.Exclude;

public class MemoModel {

   private String tittle;
   private String memoimage;
   private String date;
   private String key;


    public MemoModel() {


    }



    public MemoModel(String tittle, String memoimage, String date) {
        this.tittle = tittle;
        this.memoimage = memoimage;
        this.date = date;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getMemoimage() {
        return memoimage;
    }

    public void setMemoimage(String memoimage) {
        this.memoimage = memoimage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
