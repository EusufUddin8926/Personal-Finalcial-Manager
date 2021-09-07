package com.example.apersonalfinancialmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsertWithdrawal extends AppCompatActivity {

    private TextInputEditText amountEdittext,noteEditetxt,sourceEdittext;
    private TextView  setdate;
    private ImageView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Button saveWithdrawalButton;

    DatabaseReference reference;
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_withdrawal);
        noteEditetxt = findViewById(R.id.noteEditext);
        amountEdittext = findViewById(R.id.amountEditext);
        sourceEdittext = findViewById(R.id.sourceEditext);
        mDisplayDate = findViewById(R.id.datepickerId);
        setdate = findViewById(R.id.setDate);
        saveWithdrawalButton = findViewById(R.id.buttonSave);

        AppCompatImageView bactbtn = findViewById(R.id.backBtnWithdrawal);
        bactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.Savings.class));
                // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
                finish();
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users/" + currentUser.getUid());

        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setdate.setText(date.format(new Date()));


        saveWithdrawalButton.setOnClickListener(new View.OnClickListener() {
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
                        com.example.apersonalfinancialmanager.InsertWithdrawal.this,
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
        if(category.isEmpty()){
            sourceEdittext.setError(getResources().getString(R.string.iw_withdrawal_catagory));
            sourceEdittext.requestFocus();
            return;
        }

        if(amount.isEmpty())
        {
            amountEdittext.setError(getResources().getString(R.string.iw_withdrawal_amount));
            amountEdittext.requestFocus();
            return;
        }
        String dpamt = getIntent().getStringExtra("dpamt");
        String wtamt = amountEdittext.getText().toString().trim();
        double idpamt= Double.parseDouble(dpamt);
        double iwtamt= Double.parseDouble(wtamt);
        DecimalFormat df = new DecimalFormat("00.00");
        if(iwtamt>idpamt)
            {
               // float number = Math.round(dpamt*100)/100; String FormatednUm= Float.toString(number);
                Toast mytoast0 = Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                String ertxt= getResources().getString(R.string.iw_withdrawal_amount_Limit_toast);
                mytoast0.setText(ertxt+df.format(idpamt)+"\u09F3");
                mytoast0.show();
                amountEdittext.setError(getResources().getString(R.string.iw_withdrawal_amount_Limit)+df.format(idpamt)+"\u09F3" );
                amountEdittext.requestFocus();
                return;
            }

        reference.child("SonchoyT").child(milis).child("category").setValue(category);
        reference.child("SonchoyT").child(milis).child("date").setValue(date);
        reference.child("SonchoyT").child(milis).child("note").setValue(note);
        reference.child("SonchoyT").child(milis).child("deposit").setValue("");
        reference.child("SonchoyT").child(milis).child("withdrawal").setValue(amount);

        //Toast.makeText(getBaseContext(), R.string.rcExpenseSucces, Toast.LENGTH_SHORT).show();

        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
        mytoast.setText(R.string.withdrawalSucces);
        mytoast.show();
        backhome();
    }

    private void backhome(){

        Intent intent = new Intent(getBaseContext(), com.example.apersonalfinancialmanager.Savings.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.Savings.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }




}

