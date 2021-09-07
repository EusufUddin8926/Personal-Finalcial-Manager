package com.example.apersonalfinancialmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class CustomerYouGotPage extends AppCompatActivity {
    private static final String TAG = "CustomerYouGotPage";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;

    private TextView  setdate;
    private ImageView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_you_got_page);

        final String custname = getIntent().getStringExtra("custname");
        final String phonenumber = getIntent().getStringExtra("phonenumber");

        //initialize the firebase objects
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users/"+currentUser.getUid()+"/DP_Customers/"+custname);

        final TextView showAmount = findViewById(R.id.youGotAmount);
        TextView receiverNameTextView = findViewById(R.id.senderName);
        receiverNameTextView.setText(custname);

        mDisplayDate = findViewById(R.id.datepickerId);
        setdate = findViewById(R.id.setDate);

        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setdate.setText(date.format(new Date()));

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        com.example.apersonalfinancialmanager.CustomerYouGotPage.this,
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

        TextInputEditText enterNote= findViewById(R.id.enterNoteUGotPage);
        final TextInputEditText enterAmount = findViewById(R.id.enterAmountUGotPage);

        enterAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showAmount.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0)
                {
                    showAmount.setText("0");
                }
            }
        });

        //back Button on click listner
        AppCompatImageView backBtnYouGotPage = findViewById(R.id.backBtnYouGotPage);
        backBtnYouGotPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.apersonalfinancialmanager.CustomerYouGotPage.this,CustomerPage.class);
                intent.putExtra("custname",custname);
                intent.putExtra("phonenumber",phonenumber);
                startActivity(intent);
                finish();
            }
        });

        //youGotSaveBtn onClickListner
        AppCompatButton youGotSaveBtn  = findViewById(R.id.youGotSaveBtn);
        youGotSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = enterAmount.getText().toString().trim();
                String date = setdate.getText().toString().trim();
                String milis = Long.toString(System.currentTimeMillis());
                String Note = enterNote.getText().toString().trim();

                if(amount.isEmpty())
                {
                    enterAmount.setError(getResources().getString(R.string.iw_got_amount));
                    enterAmount.requestFocus();
                    return;
                }

                reference.child("denaPawnaT").child(milis).child("yougot").setValue(amount);
                reference.child("denaPawnaT").child(milis).child("yougave").setValue("");
                reference.child("denaPawnaT").child(milis).child("date").setValue(date);
                reference.child("denaPawnaT").child(milis).child("note").setValue(Note);

                Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
                mytoast.setText(R.string.cpgotSucces);
                mytoast.show();

                Intent intent = new Intent(com.example.apersonalfinancialmanager.CustomerYouGotPage.this,CustomerPage.class);
                intent.putExtra("custname",custname);
                intent.putExtra("phonenumber",phonenumber);
                startActivity(intent);
                finish();
            }
        });
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

}