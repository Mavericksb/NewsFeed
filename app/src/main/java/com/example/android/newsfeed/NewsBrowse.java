package com.example.android.newsfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by ROBERTO on 06/04/2017.
 */

public class NewsBrowse extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_web);

        WebView newsWeb = (WebView)findViewById(R.id.web_view);
        Intent intent = getIntent();
        String articleUrl = intent.getStringExtra("url");
        newsWeb.loadUrl(articleUrl);

    }
}

