package com.example.publicview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pd;
    WebView web;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading........");
        pd.show();
        url = "http://192.168.29.20/publicView/";

        web = findViewById(R.id.web);

        WebSettings websettings = web.getSettings();
        websettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap fav) {
                super.onPageStarted(view, url, fav);
                invalidateOptionsMenu();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                web.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                if(pd.isShowing())
                    pd.dismiss();
                super.onPageFinished(view, url);
                invalidateOptionsMenu();
            }


            public void onReceivedError(WebView view, int error, String desc  , String fail){
                web.loadUrl("file:///android_asset/custom.html");
                invalidateOptionsMenu();

            }
        });
        web.clearCache(true);
        web.clearHistory();
        web.loadUrl(url);
    }

        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.options, menu);
            return super.onCreateOptionsMenu(menu);
        }
        public boolean onPrepareOptionsMenu ( Menu menu){
        if(!web.canGoBack())
            menu.getItem(1).setEnabled(false);
        else
            menu.getItem(1).setEnabled(true);
        if(!web.canGoForward())
            menu.getItem(2).setEnabled(false);
        else
            menu.getItem(2).setEnabled(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                web.loadUrl(url);
                break;
            case R.id.back:
                if (web.canGoBack()) {
                    web.goBack();
                }
                break;
            case R.id.forward:
                if (web.canGoForward()) {
                    web.goForward();
                }


        }
        return super.onOptionsItemSelected(item);
    }
}
