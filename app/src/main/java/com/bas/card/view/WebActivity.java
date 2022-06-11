package com.bas.card.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.bas.card.databinding.ActivityWebBinding;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.StaticData;



public class WebActivity extends AppCompatActivity {

    private ActivityWebBinding binding;
    private Context context;
    String gameUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();

        initUi();
    }

    private void initUi() {
        //  getUrlData();

        Intent intent = getIntent();
        if (intent.hasExtra(StaticData.CATEGORY_PLAY_GAME)) {
            gameUrl = intent.getStringExtra(StaticData.CATEGORY_PLAY_GAME);
            Log.d("TAG", gameUrl);

        }

        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setDomStorageEnabled(true);

        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                binding.webView.loadUrl(url);
                return true;
            }

        });
        binding.webView.loadUrl(gameUrl);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NetworkUtil.setCustomIntent(WebActivity.this);
    }
}