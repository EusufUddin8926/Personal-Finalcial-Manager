package com.example.apersonalfinancialmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    ImageView top_curve;
    EditText eMail,password;
    TextView email_text, password_text, login_title;
    TextView logo;
    LinearLayout new_user_layout;
    CardView login_card;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FirebaseUser firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            startActivity(new Intent(this,MainActivity.class));
        }

        top_curve = findViewById(R.id.top_curve);
        eMail = findViewById(R.id.email);
        email_text = findViewById(R.id.email_text);
        password = findViewById(R.id.password);
        password_text = findViewById(R.id.password_text);
        logo = findViewById(R.id.logo);
        login_title = findViewById(R.id.login_text);
        new_user_layout = findViewById(R.id.new_user_text);
        login_card = findViewById(R.id.login_card);

        Animation top_curve_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_down);
        top_curve.startAnimation(top_curve_anim);

        Animation editText_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.edittext_anim);
        eMail.startAnimation(editText_anim);
        password.startAnimation(editText_anim);

        Animation field_name_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.field_name_anim);
        email_text.startAnimation(field_name_anim);
        password_text.startAnimation(field_name_anim);
        logo.startAnimation(field_name_anim);
        login_title.startAnimation(field_name_anim);

        Animation center_reveal_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.center_reveal_anim);
        login_card.startAnimation(center_reveal_anim);

        Animation new_user_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down_top);
        new_user_layout.startAnimation(new_user_anim);

        mAuth = FirebaseAuth.getInstance();
    }
    public void register(View view) {
       startActivity(new Intent(this,Registration.class));
    }

    public void loginButton(View view) {

        loginUser();
        /*Toast.makeText(this,"Login Clicked",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));*/
    }

    private void loginUser() {
        String email = eMail.getText().toString();
        String pass = password.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!pass.isEmpty()){
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(com.example.apersonalfinancialmanager.Login.this, "Login Successfully !!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(com.example.apersonalfinancialmanager.Login.this , MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.example.apersonalfinancialmanager.Login.this, "Login Failed !!", Toast.LENGTH_SHORT).show();


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

    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.Login.this);
        alertDialogBuilder.setMessage(R.string.alertBuilderAppClose);
        alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(com.example.apersonalfinancialmanager.Login.this, R.string.alertBuilderYesAppCloseConfirmation, Toast.LENGTH_SHORT).show();

                finishAffinity();
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.alertBuilderNo,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}