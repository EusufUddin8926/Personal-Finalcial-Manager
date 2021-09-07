package com.example.apersonalfinancialmanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;

    DatabaseReference reference;
    FirebaseUser currentUser;
    @Override
    protected void onStart() {
        super.onStart();
     /*   FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser!=null){}

        else {
            //   Intent intent=new Intent(this, Login.class);
            startActivity(new Intent(this,Login.class));
        }*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* setTheme(R.style.AppTheme);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.setTitle("Amar Hisab");

        mAuth=FirebaseAuth.getInstance();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        /*if (currentUser==null){
            startActivity(new Intent(this,Login.class));
        }*/
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        reference =database.getReference().child("users").child(currentUser.getUid()).child("UserInfo");



        initialview();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,new HomeFragment());
        transaction.commit();



        View hView =  navigationView.inflateHeaderView(R.layout.navigation_header);
        ImageView imgvw = (ImageView)hView.findViewById(R.id.navheaderimageId);
        TextView username = (TextView)hView.findViewById(R.id.navusernameId);
        TextView usermail = (TextView)hView.findViewById(R.id.navuseremailId);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String image = snapshot.child("image").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();

                    Glide.with(com.example.apersonalfinancialmanager.MainActivity.this).load(image).centerCrop()
                            .placeholder(R.drawable.amarhisab).into(imgvw);
                    username.setText(name);
                    usermail.setText(email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void initialview(){
        drawer =findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.naviagtiondrawer);
        Menu menu =navigationView.getMenu();



    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                drawer.closeDrawer(Gravity.LEFT);
                break;

            case R.id.profile:
                Toast.makeText(this,"Profile Selected ",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(com.example.apersonalfinancialmanager.MainActivity.this, com.example.apersonalfinancialmanager.UserProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.denapawna:
                Intent intentdp =new Intent(com.example.apersonalfinancialmanager.MainActivity.this, DenaPawna.class);
                startActivity(intentdp);
                finish();

                break;
            case R.id.jomaK:
                Intent intentjk =new Intent(com.example.apersonalfinancialmanager.MainActivity.this, com.example.apersonalfinancialmanager.RevenueCost.class);
                startActivity(intentjk);
                finish();
                break;
                case R.id.soncoy:
                Intent intentsn =new Intent(com.example.apersonalfinancialmanager.MainActivity.this, com.example.apersonalfinancialmanager.Savings.class);
                startActivity(intentsn);
                finish();
                break;
                case R.id.memoR:
               Intent intentmr =new Intent(com.example.apersonalfinancialmanager.MainActivity.this, com.example.apersonalfinancialmanager.MemoRoshid.class);
                startActivity(intentmr);
                finish();
                break;

            case R.id.settings:
                Intent intents =new Intent(com.example.apersonalfinancialmanager.MainActivity.this, com.example.apersonalfinancialmanager.Settings.class);
                startActivity(intents);
                finish();


                break;
            case R.id.about:
                Toast mytoast = Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG);
                mytoast.setText(R.string.settings_Developers0);
                mytoast.show();

                break;
            case R.id.logout:

                FirebaseAuth.getInstance().signOut();

                Intent intentx = new Intent(com.example.apersonalfinancialmanager.MainActivity.this, com.example.apersonalfinancialmanager.Login.class);
                intentx.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intentx);
                break;


            case R.id.navheaderimageId:


                break;



            default:
                break;
        }


        return false;
    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(com.example.apersonalfinancialmanager.MainActivity.this);
        alertDialogBuilder.setMessage(R.string.alertBuilderAppClose);
        alertDialogBuilder.setPositiveButton(R.string.alertBuilderYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(com.example.apersonalfinancialmanager.MainActivity.this, R.string.alertBuilderYesAppCloseConfirmation, Toast.LENGTH_SHORT).show();

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