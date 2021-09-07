package com.example.apersonalfinancialmanager;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class DenaPawna extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dena_pawna);

        ListFragment fragment = new ListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,fragment);
        fragmentTransaction.commit();

        AppCompatImageView backBtnDP = findViewById(R.id.backBtnDenaPawnaPage);
        FloatingActionButton addCustomerButton = findViewById(R.id.addCustomerBtn);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new com.example.apersonalfinancialmanager.DenaPawnaFragment()).commit();

        //Get the intent , verify the action and get the query
        final Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch();
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                   com.example.apersonalfinancialmanager.MySuggestionProvider.AUTHORITY, com.example.apersonalfinancialmanager.MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);


        }

        //addCustomerBtn on click Listner
        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(com.example.apersonalfinancialmanager.DenaPawna.this, com.example.apersonalfinancialmanager.AddCustomerPage.class);
                startActivity(intent1);
                finish();
            }
        });

        backBtnDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.MainActivity.class));
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.MainActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }


}