package com.bas.card.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bas.card.R;
import com.bas.card.databinding.ActivityStartBinding;
import com.bas.card.util.InterstialAdInterface;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.StaticData;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityStartBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();


        initUi();
        initClickListener();

    }

    private void initUi() {
        setUpNativeAd();
        setUpBannerAd();
    }

    //* Set Native Ads *//
    private void setUpNativeAd() {

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.NATIVE_SMALL_AD_status,
                1);
        if (adStatus == 1) {


            String adId = NetworkUtil.getSharedPreferenceData(context,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.NATIVE_SMALL_AD,
                    "");
            NetworkUtil.loadNativeAd(binding.startScreenNativeSmallAd, context, adId);
        }
    }

    //* Set Banner Ads *//
    private void setUpBannerAd() {

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.BANNER_AD_status,
                1);
        if (adStatus == 1) {


            String adId = NetworkUtil.getSharedPreferenceData(context,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.BANNER_AD,
                    "");
            NetworkUtil.loadBannerAdSetup(this, context, adId, binding.startScreenBannerSmallAd);
        }
    }

    private void initClickListener() {
        binding.img1.setOnClickListener(this);
        binding.img2.setOnClickListener(this);
        binding.img3.setOnClickListener(this);
        binding.img4.setOnClickListener(this);
        binding.img5.setOnClickListener(this);
        binding.img6.setOnClickListener(this);
        binding.img7.setOnClickListener(this);
        binding.img8.setOnClickListener(this);
        binding.nativeAdStartActivity.setOnClickListener(this);
        binding.startScreenButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img1:
            case R.id.img2:
            case R.id.img3:
            case R.id.img4:
            case R.id.img5:
            case R.id.img6:
            case R.id.img7:
            case R.id.img8:
            case R.id.nativeAdStartActivity:
                NetworkUtil.setCustomIntent(StartActivity.this);
                break;
            case R.id.startScreenButton:
                if (NetworkUtil.networkConnect(context)) {
//                    Intent intent = NetworkUtil.navActivity(StartActivity.this, LonchAppActivity.class);
//                    startActivity(intent);
                    interstitialAd(LonchAppActivity.class);
                } else {
                    NetworkUtil.snackBarShow(binding.startLayout, context.getString(R.string.turn_on_internet_message));
                }
                break;
        }
    }
    //* Interstitial Ad *//
    public void interstitialAd(Class className) {
        AlertDialog alertDialog = NetworkUtil.createLoaderAlertDialog(StartActivity.this);

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.INTERSTITIAL_AD_status,
                1);

        if(adStatus == 1)
        {
            String adId = NetworkUtil.getSharedPreferenceData(StartActivity.this,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.INTERSTITIAL_AD,
                    "");

            NetworkUtil.loadInterstialAd(adId, StartActivity.this, new InterstialAdInterface() {
                @Override
                public void interstialAdStatus(boolean status) {
                    if (status) {
                        if (NetworkUtil.networkConnect(context)) {
                            Intent intent = NetworkUtil.navActivity(StartActivity.this, className);
                            startActivity(intent);
                        } else {
                            NetworkUtil.snackBarShow(binding.startLayout, getResources().getString(R.string.turn_on_internet_message));

                        }
                    }
                }

                @Override
                public void isInterstialAdLoaded(boolean loadedStatus) {
                    if (loadedStatus) {
                        alertDialog.dismiss();
                    }
                }
            });
        }
        else {
            Intent intent = NetworkUtil.navActivity(StartActivity.this, className);
            startActivity(intent);
        }

    }
}