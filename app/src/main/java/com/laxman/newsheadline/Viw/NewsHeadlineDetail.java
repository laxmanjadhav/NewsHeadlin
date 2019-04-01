package com.laxman.newsheadline.Viw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.laxman.newsheadline.MainViewInterface.MainViewInterface;
import com.laxman.newsheadline.R;

import butterknife.BindView;

public class NewsHeadlineDetail extends AppCompatActivity implements MainViewInterface {

    WebView wv_nws;
    String NwsUrl;
    @BindView(R.id.simpleProgressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nwsdtails);
        wv_nws = (WebView) findViewById(R.id.wv_nwsdtail);

        Intent intent = getIntent();
        NwsUrl = intent.getStringExtra("Url");
        if (NetworkStatusClass.isInternetAvailable(getApplicationContext())){
            wv_nws.setWebChromeClient(new WebChromeClient());

            WebSettings webSettings = wv_nws.getSettings();
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setSupportMultipleWindows(true);
            wv_nws.setWebViewClient(new WebViewClient());
            wv_nws.loadUrl(NwsUrl);
        }else {
            Toast.makeText(NewsHeadlineDetail.this,"No internet is available",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayError(String s) {

    }
}
