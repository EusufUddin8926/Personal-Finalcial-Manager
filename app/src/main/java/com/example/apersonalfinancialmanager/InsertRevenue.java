package com.example.apersonalfinancialmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsertRevenue extends AppCompatActivity {

    private static final String TAG = "InsertRevenue";

    private TextInputEditText amountEdittext,noteEditetxt,sourceEdittext;
    private TextView  setdate;
    private ImageView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private AppCompatButton saverevenueButton;
    DatabaseReference reference;
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_revenue);

        noteEditetxt = findViewById(R.id.noteEditext);
        amountEdittext = findViewById(R.id.amountEditext);
        sourceEdittext = findViewById(R.id.sourceEditext);
        mDisplayDate = findViewById(R.id.datepickerId);
        setdate = findViewById(R.id.setDate);
        saverevenueButton = findViewById(R.id.buttonSave);

        AppCompatImageView bactbtn = findViewById(R.id.backBtnDeposit);
        bactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.RevenueCost.class));
                // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
                finish();
            }
        });




        //reference = FirebaseDatabase.getInstance().getReference().child("Revenue");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users/" + currentUser.getUid());

        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setdate.setText(date.format(new Date()));




        saverevenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdata();
            }
        });







        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        com.example.apersonalfinancialmanager.InsertRevenue.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makedateString(day, month, year);
                setdate.setText(date);
            }
        };
    }

    private String makedateString(int day, int month, int year){
        return  getDayformat(day) +" "+ getMonthformat(month) +" "+year;
    }

    private String getMonthformat(int s) {
        if(s== 1){
            return "Jan";
        }
        if(s== 2){
            return "Feb";
        }
        if(s== 3){
            return "Mar";
        }
        if(s== 4){
            return "Apr";
        }
        if(s== 5){
            return "May";
        }
        if(s== 6){
            return "Jun";
        }
        if(s== 7){
            return "Jul";
        }
        if(s== 8){
            return "Aug";
        }
        if(s== 9){
            return "Sep";
        }
        if(s== 10){
            return "Oct";
        }
        if(s== 11){
            return "Nov";
        }
        if(s== 12){
            return "Dec";
        }
        return null;
    }

    private String getDayformat(int s) {
        if(s== 1) return "01";
        if(s== 2) return "02";
        if(s== 3) return "03";
        if(s== 4) return "04";
        if(s== 5) return "05";
        if(s== 6) return "06";
        if(s== 7) return "07";
        if(s== 8) return "08";
        if(s== 9) return "09";

        return String.valueOf(s);
    }




    private void insertdata() {


        String category = sourceEdittext.getText().toString().trim();
        String amount = amountEdittext.getText().toString().trim();
        String date =   setdate.getText().toString().trim();
        String note = noteEditetxt.getText().toString().trim();
        String milis = Long.toString(System.currentTimeMillis());

        if(category.isEmpty())
        {
            sourceEdittext.setError(getResources().getString(R.string.iw_revenue_catagory));
            sourceEdittext.requestFocus();
            return;
        }

        if(amount.isEmpty())
        {
            amountEdittext.setError(getResources().getString(R.string.iw_revenue_amount));
            amountEdittext.requestFocus();
            return;
        }


        reference.child("JomaKhorochT").child(milis).child("category").setValue(category);
        reference.child("JomaKhorochT").child(milis).child("date").setValue(date);
        reference.child("JomaKhorochT").child(milis).child("note").setValue(note);
        reference.child("JomaKhorochT").child(milis).child("revenue").setValue(amount);
        reference.child("JomaKhorochT").child(milis).child("expense").setValue("");



       /* RevenueModel revenueModel = new RevenueModel(amount,note,source,date);
        reference.push().setValue(revenueModel);*/
       // Toast.makeText(this, R.string.rcRevenueSucces, Toast.LENGTH_SHORT).show();

        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
        mytoast.setText(R.string.rcRevenueSucces);
        mytoast.show();

        backhome();



    }

    private void backhome(){

        Intent intent = new Intent(com.example.apersonalfinancialmanager.InsertRevenue.this, com.example.apersonalfinancialmanager.RevenueCost.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.RevenueCost.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }


}