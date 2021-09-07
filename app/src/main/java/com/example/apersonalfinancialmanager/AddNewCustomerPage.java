package com.example.apersonalfinancialmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewCustomerPage extends AppCompatActivity {

    TextInputEditText custName,custPhoneNumber,custAddress;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer_page);

        //initialize the objects
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users/"+currentUser.getUid());

        //back button onclick listner
        ImageView backBtnAddNewCustomer = findViewById(R.id.backBtnNewCustomer);
        backBtnAddNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),AddCustomerPage.class);
                startActivity(intent);
                finish();
            }
        });


        custName = findViewById(R.id.customer_name_add_cust);
        custPhoneNumber = findViewById(R.id.customer_phone_number_add_cust);
        custAddress = findViewById(R.id.customer_address_add_cust);

        //save button onclick listner
        AppCompatButton addNewCustomerSaveBtn  = findViewById(R.id.addNewCustomerSaveBtn);
        addNewCustomerSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String custname = custName.getText().toString().trim();
                String custphonenumber = custPhoneNumber.getText().toString().trim();
                String custaddress = custAddress.getText().toString().trim();

                if(custname.isEmpty())
                {
                    custName.setError(getResources().getString(R.string.iw_addCus_Name));
                    custName.requestFocus();
                    return;
                }

                if(custphonenumber.isEmpty())
                {
                    custPhoneNumber.setError(getResources().getString(R.string.iw_addCus_Phone));
                    custPhoneNumber.requestFocus();
                    return;
                }

                if(custphonenumber.length() > 11 || custphonenumber.length() < 11)
                {
                    custPhoneNumber.setError("Enter valid phone number");
                    custPhoneNumber.requestFocus();
                    return;
                }

                custphonenumber = "+88" + custphonenumber;

                reference.child("DP_Customers/"+custname).child("name").setValue(custname);
                reference.child("DP_Customers/"+custname).child("phonenumber").setValue(custphonenumber);
                reference.child("DP_Customers/"+custname).child("z_address").setValue(custaddress);

                Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
                mytoast.setText(R.string.cpAddnewCusSucces);
                mytoast.show();

                Intent intent = new Intent(com.example.apersonalfinancialmanager.AddNewCustomerPage.this,CustomerPage.class);
                intent.putExtra("custname",custname);
                intent.putExtra("phonenumber",custphonenumber);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(),AddCustomerPage.class);
        startActivity(intent);
        finish();
    }
}