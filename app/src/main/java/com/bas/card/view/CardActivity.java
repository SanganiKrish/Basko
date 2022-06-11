package com.bas.card.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bas.card.R;
import com.bas.card.databinding.ActivityCardBinding;
import com.bas.card.util.InterstialAdInterface;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.StaticData;

public class CardActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityCardBinding binding;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
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
        binding.bannerAdCardScreen.setOnClickListener(this);
        binding.nativeAdCardActivity.setOnClickListener(this);
        binding.cardImg1.setOnClickListener(this);
        binding.cardImg2.setOnClickListener(this);
        binding.cardImg3.setOnClickListener(this);
        binding.cardImg4.setOnClickListener(this);
        binding.cardImg5.setOnClickListener(this);
        binding.cardImg6.setOnClickListener(this);

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
            NetworkUtil.loadNativeAd(binding.cardScreenNativeSmallAd, context, adId);
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
            NetworkUtil.loadBannerAdSetup(this, context, adId, binding.cardScreenBannerSmallAd);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bannerAdCardScreen:
            case R.id.nativeAdCardActivity:
                NetworkUtil.setCustomIntent(CardActivity.this);

                break;
            case R.id.cardImg2:
//                Intent intentCard2 = new Intent(CardActivity.this, DetailActivity.class);
//                intentCard2.putExtra(StaticData.INTENT_KEY, "card2");
//                startActivity(intentCard2);
                interstitialAd("card2");
                break;
            case R.id.cardImg3:
//                Intent intentCard3 = new Intent(CardActivity.this, DetailActivity.class);
//                intentCard3.putExtra(StaticData.INTENT_KEY, "card3");
//                startActivity(intentCard3);
                interstitialAd("card3");

                break;
            case R.id.cardImg4:
//                Intent intentCard4 = new Intent(CardActivity.this, DetailActivity.class);
//                intentCard4.putExtra(StaticData.INTENT_KEY, "card4");
//                startActivity(intentCard4);
                interstitialAd("card4");

                break;
            case R.id.cardImg5:
//                Intent intentCard5 = new Intent(CardActivity.this, DetailActivity.class);
//                intentCard5.putExtra(StaticData.INTENT_KEY, "card5");
//                startActivity(intentCard5);
                interstitialAd("card5");

                break;
            case R.id.cardImg6:
//                Intent intentCard6 = new Intent(CardActivity.this, DetailActivity.class);
//                intentCard6.putExtra(StaticData.INTENT_KEY, "card6");
//                startActivity(intentCard6);
                interstitialAd("card6");

        }
    }

    //* Interstitial Ad *//
    public void interstitialAd(String value) {
        AlertDialog alertDialog = NetworkUtil.createLoaderAlertDialog(CardActivity.this);

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.INTERSTITIAL_AD_status,
                1);

        if (adStatus == 1) {
            String adId = NetworkUtil.getSharedPreferenceData(CardActivity.this,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.INTERSTITIAL_AD,
                    "");

            NetworkUtil.loadInterstialAd(adId, CardActivity.this, new InterstialAdInterface() {
                @Override
                public void interstialAdStatus(boolean status) {
                    if (status) {
                        if (NetworkUtil.networkConnect(context)) {
                            Intent intent = NetworkUtil.navActivity(CardActivity.this, DetailActivity.class);
                            intent.putExtra(StaticData.INTENT_KEY, value);
                            startActivity(intent);
                        } else {
                            NetworkUtil.snackBarShow(binding.cardLayout, getResources().getString(R.string.turn_on_internet_message));

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
        } else {
            Intent intent = NetworkUtil.navActivity(CardActivity.this, DetailActivity.class);
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NetworkUtil.setCustomIntent(CardActivity.this);
    }
}