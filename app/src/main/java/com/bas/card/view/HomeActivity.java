package com.bas.card.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bas.card.R;
import com.bas.card.databinding.ActivityHomeBinding;
import com.bas.card.util.InterstialAdInterface;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.RewardAdInterface;
import com.bas.card.util.StaticData;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityHomeBinding binding;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();

        initUi();
        initClickListener();
    }

    private void initUi() {
        setUpNativeAd();
        setUpBannerAd();
        setUpAlertNativeAd();
    }

    private void initClickListener() {
        binding.bannerAdHomeScreen.setOnClickListener(this);
        binding.bannerAd2HomeScreen.setOnClickListener(this);
        binding.nativeAdHomeActivity.setOnClickListener(this);


        binding.startHomeButton.setOnClickListener(this);
        binding.earnCoinbutton.setOnClickListener(this);
        binding.shareButton.setOnClickListener(this);


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
            NetworkUtil.loadNativeAd(binding.homeScreenNativeSmallAd, context, adId);
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
            NetworkUtil.loadBannerAdSetup(this, context, adId, binding.homeScreenBannerSmallAd);
        }
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.bannerAdHomeScreen:
            case R.id.bannerAd2HomeScreen:
            case R.id.nativeAdHomeActivity:
            case R.id.shareButton:
                NetworkUtil.setCustomIntent(HomeActivity.this);

                break;

            case R.id.startHomeButton:
            case R.id.earnCoinbutton:

                if (NetworkUtil.networkConnect(context)) {
//                    Intent intent = NetworkUtil.navActivity(HomeActivity.this, UnLockAppActivity.class);
//                    startActivity(intent);
                    interstitialAd(UnLockAppActivity.class);
                } else {
                    NetworkUtil.snackBarShow(binding.homeLayout, context.getString(R.string.turn_on_internet_message));

                }
                break;
        }
    }
    //* Interstitial Ad *//
    public void interstitialAd(Class className) {
        AlertDialog alertDialog = NetworkUtil.createLoaderAlertDialog(HomeActivity.this);

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.INTERSTITIAL_AD_status,
                1);

        if(adStatus == 1)
        {
            String adId = NetworkUtil.getSharedPreferenceData(HomeActivity.this,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.INTERSTITIAL_AD,
                    "");

            NetworkUtil.loadInterstialAd(adId, HomeActivity.this, new InterstialAdInterface() {
                @Override
                public void interstialAdStatus(boolean status) {
                    if (status) {
                        if (NetworkUtil.networkConnect(context)) {
                            Intent intent = NetworkUtil.navActivity(HomeActivity.this, className);
                            startActivity(intent);
                        } else {
                            NetworkUtil.snackBarShow(binding.homeLayout, getResources().getString(R.string.turn_on_internet_message));

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
            Intent intent = NetworkUtil.navActivity(HomeActivity.this, className);
            startActivity(intent);
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            showAlertDialogBox();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showAlertDialogBox() {


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView textPositiveButtonYes, textNegativeButtonNo;
        FrameLayout alertNativeAd;
        textPositiveButtonYes = dialog.findViewById(R.id.buttonYes);
        textNegativeButtonNo = dialog.findViewById(R.id.buttonNo);
        alertNativeAd = dialog.findViewById(R.id.alertNativeSmallAd);

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.NATIVE_SMALL_AD_status,
                1);
        if (adStatus == 1) {

            String adId = NetworkUtil.getSharedPreferenceData(this,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.NATIVE_SMALL_AD,
                    "");
            NetworkUtil.loadNativeAd(alertNativeAd, context, adId);
        }

        // Button Click Yes
        textPositiveButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                loadRewardAd(ThankYouActivity.class);
            }
        });

        // Button Click No
        textNegativeButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    //* Alert DiaLog Box Native ad *//
    private void setUpAlertNativeAd() {

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.NATIVE_SMALL_AD_status,
                1);
        if (adStatus == 1) {

            String adId = NetworkUtil.getSharedPreferenceData(this,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.NATIVE_SMALL_AD,
                    "");
            NetworkUtil.loadNativeAd(binding.homeScreenNativeSmallAd, context, adId);
        }
    }

    //* Load Reward Ad *//
    private void loadRewardAd(Class name) {
        AlertDialog alertDialog = NetworkUtil.createLoaderAlertDialog(HomeActivity.this);

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.REWARD_AD_status,
                1);
        if (adStatus == 1) {
            String adId = NetworkUtil.getSharedPreferenceData(context,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.REWARD_AD,
                    "");

            NetworkUtil.loadRewardAd(HomeActivity.this, adId, new RewardAdInterface() {
                @Override
                public void isRewardAdLoaded(boolean loadStatus) {
                    // Loading
                    if (loadStatus) {
                        alertDialog.dismiss();
                    }
                }

                @Override
                public void rewardAdStatus(boolean status) {
                    if (status) {
                        //   isAdShow = true;
                        Intent intent = NetworkUtil.navActivity(HomeActivity.this, name);
                        startActivity(intent);
                    }
                }
            });
        }

    }
}