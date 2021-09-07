package com.example.apersonalfinancialmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registration extends AppCompatActivity {

    ImageView top_curve;
    EditText name, eMail, password;
    TextView name_text, email_text, password_text, login_title;
    TextView logo;
    LinearLayout already_have_account_layout;
    CardView register_card;

    /*new*/
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseUser user;
    com.example.apersonalfinancialmanager.UserHelperClass helperClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        top_curve = findViewById(R.id.top_curve);
        name = findViewById(R.id.name);
        name_text = findViewById(R.id.name_text);
        eMail = findViewById(R.id.email);
        email_text = findViewById(R.id.email_text);
        password = findViewById(R.id.password);
        password_text = findViewById(R.id.password_text);
        logo = findViewById(R.id.logo);
        login_title = findViewById(R.id.registration_title);
        already_have_account_layout = findViewById(R.id.already_have_account_text);
        register_card = findViewById(R.id.register_card);

        Animation top_curve_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        top_curve.startAnimation(top_curve_anim);

        Animation editText_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.edittext_anim);
        name.startAnimation(editText_anim);
        eMail.startAnimation(editText_anim);
        password.startAnimation(editText_anim);

        Animation field_name_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.field_name_anim);
        name_text.startAnimation(field_name_anim);
        email_text.startAnimation(field_name_anim);
        password_text.startAnimation(field_name_anim);
        logo.startAnimation(field_name_anim);

        login_title.startAnimation(field_name_anim);
        Animation center_reveal_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.center_reveal_anim);
        register_card.startAnimation(center_reveal_anim);

        Animation new_user_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down_top);
        already_have_account_layout.startAnimation(new_user_anim);



        /*SaveData*/
/*
        String uname = name.getText().toString().trim();
        String uemail = eMail.getText().toString().trim();
        String upassword = password.getText().toString().trim();*/

        mAuth = FirebaseAuth.getInstance();
        //  helperClass = new UserHelperClass(uname,uemail,upassword);

    }

    public void login(View view) {
        startActivity(new Intent(this, com.example.apersonalfinancialmanager.Login.class));
        //finish();
    }
    public void registerButton(View view) {
        mAuth.signOut();

        createUser();


    }

    private void createUser() {

        String uname= name.getText().toString();
        String email = eMail.getText().toString();
        String pass = password.getText().toString();
        String image = "https://brighterwriting.com/wp-content/uploads/icon-user-default.png";
        String phone = "";
        String address = "";
        helperClass = new com.example.apersonalfinancialmanager.UserHelperClass(uname,email,pass,image,phone,address);

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!pass.isEmpty()){
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                firebaseDatabase = FirebaseDatabase.getInstance();
                                reference = firebaseDatabase.getReference("users");
                                user = FirebaseAuth.getInstance().getCurrentUser();
                                reference.child(user.getUid()).child("UserInfo").setValue(helperClass);
                                Toast.makeText(com.example.apersonalfinancialmanager.Registration.this, "Registered Successfully !!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(com.example.apersonalfinancialmanager.Registration.this , com.example.apersonalfinancialmanager.Login.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.example.apersonalfinancialmanager.Registration.this, "Registration Error !!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                password.setError("Empty Fields Are not Allowed");
            }
        }else if(email.isEmpty()){
            eMail.setError("Empty Fields Are not Allowed");
        }else{
            eMail.setError("Pleas Enter Correct Email");
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.Login.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }
}