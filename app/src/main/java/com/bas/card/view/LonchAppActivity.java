package com.bas.card.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bas.card.R;
import com.bas.card.databinding.ActivityLonchAppBinding;
import com.bas.card.util.InterstialAdInterface;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.StaticData;

public class LonchAppActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLonchAppBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLonchAppBinding.inflate(getLayoutInflater());
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
            NetworkUtil.loadNativeAd(binding.lonchAppScreenNativeSmallAd, context, adId);
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
            NetworkUtil.loadBannerAdSetup(this, context, adId, binding.lonchScreenBannerSmallAd);
        }
    }

    private void initClickListener() {
        binding.lonchImage1.setOnClickListener(this);
        binding.lonchImage2.setOnClickListener(this);
        binding.lonchImage3.setOnClickListener(this);
        binding.lonchImage4.setOnClickListener(this);
        binding.lonchImage5.setOnClickListener(this);
        binding.lonchImage6.setOnClickListener(this);
        binding.lonchImage7.setOnClickListener(this);
        binding.lonchImage8.setOnClickListener(this);
        binding.lonchImage9.setOnClickListener(this);
        binding.lonchImage10.setOnClickListener(this);
        binding.nativeAdLonchActivity.setOnClickListener(this);
        binding.lonchScreenButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img1:
            case R.id.lonchImage1:
            case R.id.lonchImage2:
            case R.id.lonchImage3:
            case R.id.lonchImage4:
            case R.id.lonchImage5:
            case R.id.lonchImage6:
            case R.id.lonchImage7:
            case R.id.lonchImage8:
            case R.id.lonchImage9:
            case R.id.lonchImage10:
            case R.id.nativeAdLonchActivity:
                NetworkUtil.setCustomIntent(LonchAppActivity.this);
                break;
            case R.id.lonchScreenButton:
                if (NetworkUtil.networkConnect(context)) {
//                    Intent intent = NetworkUtil.navActivity(LonchAppActivity.this, HomeActivity.class);
//                    startActivity(intent);
                    interstitialAd(HomeActivity.class);
                } else {
                    NetworkUtil.snackBarShow(binding.lonchAppLayout, context.getString(R.string.turn_on_internet_message));

                }
                break;
        }
    }
    //* Interstitial Ad *//
    public void interstitialAd(Class className) {
        AlertDialog alertDialog = NetworkUtil.createLoaderAlertDialog(LonchAppActivity.this);

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.INTERSTITIAL_AD_status,
                1);

        if(adStatus == 1)
        {
            String adId = NetworkUtil.getSharedPreferenceData(LonchAppActivity.this,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.INTERSTITIAL_AD,
                    "");

            NetworkUtil.loadInterstialAd(adId, LonchAppActivity.this, new InterstialAdInterface() {
                @Override
                public void interstialAdStatus(boolean status) {
                    if (status) {
                        if (NetworkUtil.networkConnect(context)) {
                            Intent intent = NetworkUtil.navActivity(LonchAppActivity.this, className);
                            startActivity(intent);
                        } else {
                            NetworkUtil.snackBarShow(binding.lonchAppLayout, getResources().getString(R.string.turn_on_internet_message));

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
            Intent intent = NetworkUtil.navActivity(LonchAppActivity.this, className);
            startActivity(intent);
        }

    }
}