package com.example.apersonalfinancialmanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity" ;


    private EditText editText,editphone,editaddress;
    private Button updateButton;
    private TextView nameTxt,emailText,phoneTxt,addressTxt;
    private CircleImageView profileImage;
    private Context context;


    DatabaseReference reference,mref;
    FirebaseUser currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        /////find user profile field/////////

        updateButton = findViewById(R.id.UserupdateButton);
        nameTxt= findViewById(R.id.userNameId);
        phoneTxt= findViewById(R.id.userphoneId);
        addressTxt= findViewById(R.id.useraddressId);
        emailText= findViewById(R.id.userEmailId);
        profileImage= findViewById(R.id.profileImageId);




        ////////  get firbase data ////////
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference mRef= database.getReference("users").child("UserInfo");
        // StorageReference storageReference= FirebaseStorage.getInstance().getReference();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        reference =database.getReference().child("users").child(currentUser.getUid()).child("UserInfo");

        setprofile();
        AppCompatImageView backBtnViewReport = findViewById(R.id.backBtnUserProfile);
        backBtnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //////Onclick with edit

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.apersonalfinancialmanager.UserProfileActivity.this, com.example.apersonalfinancialmanager.EditProfileActivity.class);
                startActivity(intent);
            }
        });


    }


    private void setprofile() {

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();


                   Glide.with(com.example.apersonalfinancialmanager.UserProfileActivity.this).load(image).centerCrop()
                            .placeholder(R.drawable.customer_logo).into(profileImage);
                    nameTxt.setText(name);
                    emailText.setText(email);
                    phoneTxt.setText( phone);
                    addressTxt.setText(address);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(com.example.apersonalfinancialmanager.UserProfileActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                nameTxt.setText("");
                emailText.setText("");
            }
        });



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent  intent = new Intent(com.example.apersonalfinancialmanager.UserProfileActivity.this,MainActivity.class);
        startActivity(intent);
    }
}

