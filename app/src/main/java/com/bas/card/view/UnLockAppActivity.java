package com.bas.card.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bas.card.R;
import com.bas.card.databinding.ActivityUnLockAppBinding;
import com.bas.card.util.InterstialAdInterface;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.StaticData;

public class UnLockAppActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityUnLockAppBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUnLockAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();

        initUi();
        initClickListener();

    }


    private void initUi() {
        setUpBannerAd();
        setUpNativeAd();
    }


    private void initClickListener() {
        binding.lockImage1.setOnClickListener(this);
        binding.lockImage2.setOnClickListener(this);
        binding.lockImage3.setOnClickListener(this);
        binding.lockImage4.setOnClickListener(this);
        binding.lockImage5.setOnClickListener(this);
        binding.lockImage6.setOnClickListener(this);
        binding.lockImage7.setOnClickListener(this);
        binding.lockImage8.setOnClickListener(this);
        binding.nativeAdUnlockActivity.setOnClickListener(this);
        binding.buttonUnloack.setOnClickListener(this);

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
            NetworkUtil.loadNativeAd(binding.unLockScreenNativeSmallAd, context, adId);
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
            NetworkUtil.loadBannerAdSetup(this, context, adId, binding.unlockScreenBannerSmallAd);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.lockImage1:
            case R.id.lockImage2:
            case R.id.lockImage3:
            case R.id.lockImage4:
            case R.id.lockImage5:
            case R.id.lockImage6:
            case R.id.lockImage7:
            case R.id.lockImage8:
            case R.id.nativeAdUnlockActivity:
                NetworkUtil.setCustomIntent(UnLockAppActivity.this);

                break;
            case R.id.buttonUnloack:

                if (NetworkUtil.networkConnect(context)) {
//                    Intent intent = NetworkUtil.navActivity(UnLockAppActivity.this, CardActivity.class);
//                    startActivity(intent);
                    interstitialAd(CardActivity.class);
                } else {
                    NetworkUtil.snackBarShow(binding.unLoackLayout, context.getString(R.string.turn_on_internet_message));

                }
                break;
        }
    }

    //* Interstitial Ad *//
    public void interstitialAd(Class className) {
        AlertDialog alertDialog = NetworkUtil.createLoaderAlertDialog(UnLockAppActivity.this);

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.INTERSTITIAL_AD_status,
                1);

        if(adStatus == 1)
        {
            String adId = NetworkUtil.getSharedPreferenceData(UnLockAppActivity.this,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.INTERSTITIAL_AD,
                    "");

            NetworkUtil.loadInterstialAd(adId, UnLockAppActivity.this, new InterstialAdInterface() {
                @Override
                public void interstialAdStatus(boolean status) {
                    if (status) {
                        if (NetworkUtil.networkConnect(context)) {
                            Intent intent = NetworkUtil.navActivity(UnLockAppActivity.this, className);
                            startActivity(intent);
                        } else {
                            NetworkUtil.snackBarShow(binding.unLoackLayout, getResources().getString(R.string.turn_on_internet_message));

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
            Intent intent = NetworkUtil.navActivity(UnLockAppActivity.this, className);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NetworkUtil.setCustomIntent(UnLockAppActivity.this);
    }
}