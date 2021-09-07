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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class NirdesikaPdfView extends AppCompatActivity {

    private AppCompatImageView backBtnnirdesika;
    WebView webview;
    ProgressBar progressbar;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nirdesika_pdf_view);

        backBtnnirdesika = findViewById(R.id.backBtnnirdesika);

        webview = (WebView) findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbarnr);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);

        progressbar.setVisibility(View.VISIBLE);
        webview.getSettings().setJavaScriptEnabled(true);
        String filename = "https://nbr.gov.bd/uploads/publications/combinepdf_compressed.pdf";
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
                swipeRefreshLayout.setEnabled(false);
                //your code on swipe refresh
                //we are checking networking connectivity
                boolean connection = isNetworkAvailable();
                if (connection) {

                   webview.reload();
                }

            }
        });






        backBtnnirdesika.setOnClickListener(new View.OnClickListener() {
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