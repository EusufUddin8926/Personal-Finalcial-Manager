package com.example.apersonalfinancialmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerPage extends AppCompatActivity {

    FirebaseUser currentUser;
    DatabaseReference reference;
    TextView custNameTextView,phoneNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);

        custNameTextView = findViewById(R.id.cust_name_customer_page);
        phoneNumberTextView = findViewById(R.id.phonenumber_customer_page);

        final String custname = getIntent().getStringExtra("custname");
        custNameTextView.setText(custname);

        final String phonenumber = getIntent().getStringExtra("phonenumber");
        phoneNumberTextView.setText(phonenumber);

        //get data from firebase
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("users/"+currentUser.getUid()+"DP_Customers/"+custname);

        //backBtnCustomerPage onclickListner
        AppCompatImageView backBtnCustomerPage = findViewById(R.id.backBtnCustomerPage);
        backBtnCustomerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),DenaPawna.class);
                startActivity(intent);
                finish();
            }
        });

        //calling a CustomerPageFragment

        Bundle bundle = new Bundle();
        bundle.putString("customername",custname);
        bundle.putString("phonenumber",phonenumber);


         FragmentManager fm = getSupportFragmentManager();
        com.example.apersonalfinancialmanager.CustomerPageFragment fragment = new com.example.apersonalfinancialmanager.CustomerPageFragment();
        fragment.setArguments(bundle);
        fm.beginTransaction().replace(R.id.customerPageFragmentLayout,fragment).commit();


        //calling a customerPageUGave Activity
        FloatingActionButton customerPageUGaveBtn = findViewById(R.id.customerPageUGaveBtn);
        customerPageUGaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.apersonalfinancialmanager.CustomerPage.this, com.example.apersonalfinancialmanager.CustomerYouGavePage.class);
                intent.putExtra("custname",custname);
                intent.putExtra("phonenumber",phonenumber);
                startActivity(intent);
                finish();
            }
        });

        //calling a customerPageUGot Activity
        FloatingActionButton customerPageUGotBtn = findViewById(R.id.customerPageUGotBtn);
        customerPageUGotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.apersonalfinancialmanager.CustomerPage.this, com.example.apersonalfinancialmanager.CustomerYouGotPage.class);
                intent.putExtra("custname",custname);
                intent.putExtra("phonenumber",phonenumber);
                startActivity(intent);
                finish();
            }
        });

        AppCompatImageView deletecustomer =  findViewById(R.id.deletecustomerId);
        deletecustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tran_id = getIntent().getStringExtra("Transaction_id");

                /*``````````````````````````````````````aalert dialog start``````````````````````````````````````````````*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.CustomerPage.this);
                alertDialogBuilder.setMessage(R.string.alertBuilderDeleteCustomer);
                alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DatabaseReference  dTReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()+ "/DP_Customers").child(tran_id);
                        dTReference.removeValue();
                        dTReference.keepSynced(true);
                        Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_SHORT);
                        mytoast.setText(R.string.alertBuilderYesDeleteCustConf);
                        mytoast.show();
                        Intent intent1 = new Intent(getBaseContext(), DenaPawna.class);
                        startActivity(intent1);
                        finish();

                    }
                });

                alertDialogBuilder.setNegativeButton(R.string.alertBuilderNo,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                /*``````````````````````````````````````aalert dialog finish``````````````````````````````````````````````*/



            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),DenaPawna.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }


}