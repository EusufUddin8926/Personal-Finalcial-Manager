package com.example.apersonalfinancialmanager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class PoripotroPdfView extends AppCompatActivity {

    private AppCompatImageView backBtnporipotro;
    WebView webview;
    ProgressBar progressbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poripotro_pdf_view);

        backBtnporipotro = findViewById(R.id.backBtnporipotro);
        webview = (WebView)findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        textView = findViewById(R.id.textreportId);
        textView.setVisibility(View.INVISIBLE);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        String filename ="https://nbr.gov.bd/uploads/paripatra/2020_Paripatra_final_draft_dt._01_.09_.20_.pdf";
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + filename);

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                //your code on swipe refresh
                //we are checking networking connectivity

                    boolean connection = isNetworkAvailable();
                    if (connection) {
                        webview.reload();
                    } else {


                    }

                }

        });



        backBtnporipotro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),AykorActivity.class));
                finish();
            }
        });
    }

    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),AykorActivity.class));
        // overridePendingTransition(R.anim.fadeout,R.anim.noanim);
        finish();
    }
}