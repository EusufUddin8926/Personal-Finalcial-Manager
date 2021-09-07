package com.example.apersonalfinancialmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class MemoRoshidDetails extends AppCompatActivity {

    private AppCompatImageView imageView;
    private TextView titleTextview,dateTextview;
    FirebaseStorage firebaseStorage;
    com.example.apersonalfinancialmanager.MemoModel selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_roshid_details);
        AppCompatImageView bactbtn = findViewById(R.id.backBtnMRDatials);
        bactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.MemoRoshid.class));
                // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
                finish();
            }
        });


        initializedView();
        getIntentData();



    }

   private void setDatawithView(String image_url, String title, String date) {

        Picasso.get().load(image_url).into(imageView);
        titleTextview.setText(title);
        dateTextview.setText(date);
    }


    private void getIntentData() {

        Bundle bundle = getIntent().getExtras();

        String image_url = bundle.getString("image_url");
        String title = bundle.getString("title");
        String date = bundle.getString("date");

        setDatawithView(image_url, title,date);

    }

    private void initializedView() {

           imageView = findViewById(R.id.memopicIDdetails);
           titleTextview = findViewById(R.id.memoroshidTittledetailsId);
           dateTextview = findViewById(R.id.memosetDatedetails);



    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.MemoRoshid.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }
}