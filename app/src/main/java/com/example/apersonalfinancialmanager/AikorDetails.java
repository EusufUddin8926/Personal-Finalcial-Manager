package com.example.apersonalfinancialmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

public class AikorDetails extends AppCompatActivity {

    private TextView texttittle,aikordescription;
    private AppCompatImageView backBtnaikordetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aikor_details);

        texttittle = findViewById(R.id.aikortittleId);
        aikordescription = findViewById(R.id.aikordescription);
        backBtnaikordetails = findViewById(R.id.backBtnaikordetails);

        String Value = getIntent().getStringExtra("value");

        if(Value.equals("আয়কর কি?")){
            texttittle.setText("আয়কর কি?");

            aikordescription.setText(R.string.aikor_defination);

        }else if(Value.equals("কোন ব্যক্তি আয়কর প্রদানের জন্য উপযুক্ত?")){
            texttittle.setText("কোন ব্যক্তি আয়কর প্রদানের জন্য উপযুক্ত?");
            aikordescription.setText(R.string.aikor_person);

        }else if(Value.equals("আয়কর কে কেন প্রগতীশীল কর বলা হয়?")){

            texttittle.setText("আয়কর কে কেন প্রগতীশীল কর বলা হয়?");
            aikordescription.setText(R.string.aikor_progoti);

        }else if(Value.equals("আয়করের জন্য আয়ের খাত কি কি?")){

            texttittle.setText("আয়করের জন্য আয়ের খাত কি কি?");
            aikordescription.setText(R.string.aikor_khat);


        }else if(Value.equals("আয়কর রিটার্ন কারা দাখিল করবেন?")){

            texttittle.setText("আয়কর রিটার্ন কারা দাখিল করবেন?");
            aikordescription.setText(R.string.aikor_return);

        }else if(Value.equals("রিটার্ন ফর্ম কোথায় পাওয়া যায়?")){

            texttittle.setText("রিটার্ন ফর্ম কোথায় পাওয়া যায়?");
            aikordescription.setText(R.string.return_form);

        }else if(Value.equals("ই-টিআইএন কি?")){

            texttittle.setText("ই-টিআইএন কি?");
            aikordescription.setText(R.string.etin_what);

        }
        else if(Value.equals("ই-টিআইএন এ কিভাবে আবেদন করতে পারি?")){

            texttittle.setText("আমি ই-টিআইএন এর জন্য কিভাবে আবেদন করতে পারি?");
            aikordescription.setText(R.string.etin_application);

        }else if(Value.equals("প্রয়োজনীয় ডকুমেন্টস কি কি লাগে?")){

            texttittle.setText("ই-টিআইএন এর জন্য প্রয়োজনীয় ডকুমেন্টস কি কি লাগে?");
            aikordescription.setText(R.string.etin_doccuments);

        }


        backBtnaikordetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.AykorActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), com.example.apersonalfinancialmanager.AykorActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }


}