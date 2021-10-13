package com.example.doctalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Calculator1 extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator1);

        webView = (WebView)findViewById(R.id.cal_web1);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://www.google.com/search?q=calculator");
    }
}