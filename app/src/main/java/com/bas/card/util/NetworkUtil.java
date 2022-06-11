package com.bas.card.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdkUtils;
import com.bas.card.R;
import com.google.android.material.snackbar.Snackbar;

public class NetworkUtil {

    // Intent
    public static Intent navActivity(Activity activityReference, Class className) {
        Intent intent = new Intent(activityReference, className);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    // Network Connotation
    public static boolean networkConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    // Show Snack Bar
    public static void snackBarShow(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void setCustomIntent(Activity activity) {

        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
        customIntent.setToolbarColor(ContextCompat.getColor(activity, R.color.button_color));

        CustomTabColorSchemeParams params = new CustomTabColorSchemeParams.Builder()
                .build();
        customIntent.setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, params);

        setCustomChromeTab(activity, customIntent.build());
    }

    // Open Any Ads Click Event To Open Chrome //
    public static void setCustomChromeTab(Activity activity, CustomTabsIntent customTabsIntent) {
        String packageName = "com.android.chrome";
        Uri uri = Uri.parse(StaticData.QUREKA_AD_URL);
        if (packageName != null) {

            customTabsIntent.intent.setPackage(packageName);

            customTabsIntent.launchUrl(activity, uri);
        } else {

            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }


    //* Shared Preference Local Data Get *//
    public static String getSharedPreferenceData(Context context, String databaseName, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(databaseName, 0);
        String data = sharedPreferences.getString(key, defaultValue);
        return data;
    }

    //* Shared Preference Local Data Get *//
    public static int getSharedPreferenceStatus(Context context, String databaseName, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(databaseName, 0);
        int status = sharedPreferences.getInt(key, defaultValue);
        return status;
    }

    // AD Adaptive banner //
    public static void loadBannerAdSetup(Activity activity, Context context, String adId, FrameLayout frameLayout) {
        MaxAdView adView;
        adView = new MaxAdView(adId, activity );
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdExpended");
            }

            @Override
            public void onAdCollapsed(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdCollapsed");
            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdLoaded");
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdDisplayed");
                frameLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdHidden");
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdClicked");
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("APP_LOVIN_TAG","onAdFailed");
                frameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.d("APP_LOVIN_TAG","onAdDisplayedFailed");
                frameLayout.setVisibility(View.GONE);
            }
        });

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Get the adaptive banner height.
        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize( activity ).getHeight();
        int heightPx = AppLovinSdkUtils.dpToPx( activity, heightDp );

        adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
        adView.setExtraParameter( "adaptive_banner", "true" );

        frameLayout.addView( adView );

        // Load the ad
        adView.loadAd();
    }
    // Load interstitial Ad //
    public static MaxInterstitialAd interstitialAd;

    public static void loadInterstialAd(String adId, Activity activity, InterstialAdInterface adInterface) {
        AlertDialog alertDialog = NetworkUtil.createLoaderAlertDialog(activity);
        interstitialAd = new MaxInterstitialAd(adId, activity);
        MaxAdListener listener = new MaxAdListener() {

            @Override
            public void onAdLoaded(MaxAd ad) {
                alertDialog.dismiss();
                adInterface.isInterstialAdLoaded(true);
                Log.d("InterstialAdTAG", "On Ad Loaded");
                interstitialAd.showAd();
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.d("InterstialAdTAG", "On Ad Displayed");
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.d("InterstialAdTAG", "On Ad Hidden");
                adInterface.interstialAdStatus(true);
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.d("InterstialAdTAG", "On Ad Clicked");
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("InterstialAdTAG", "On Ad Failed");
                adInterface.isInterstialAdLoaded(true);
                adInterface.interstialAdStatus(true);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.d("InterstialAdTAG", "On Ad Display Failed");
                adInterface.isInterstialAdLoaded(true);
                adInterface.interstialAdStatus(true);
            }
        };
        interstitialAd.setListener(listener);
        interstitialAd.loadAd();
    }

    // Load reward Ad //
    public static MaxRewardedAd rewardedAd;

    public static void loadRewardAd(Activity activity, String adId, RewardAdInterface adInterface) {
        rewardedAd = MaxRewardedAd.getInstance(adId, activity);
        MaxRewardedAdListener listener = new MaxRewardedAdListener() {
            @Override
            public void onRewardedVideoStarted(MaxAd ad) {

            }

            @Override
            public void onRewardedVideoCompleted(MaxAd ad) {

            }

            @Override
            public void onUserRewarded(MaxAd ad, MaxReward reward) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                adInterface.isRewardAdLoaded(true);
                Log.d("RewardAdTAG", "On Ad Loaded");
                rewardedAd.showAd();
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {
                adInterface.isRewardAdLoaded(true);
                adInterface.rewardAdStatus(true);
            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("RewardAdTAG", "On Ad Failed");
                adInterface.isRewardAdLoaded(true);
                adInterface.rewardAdStatus(true);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.d("RewardAdTAG", "On Ad Failed");
                adInterface.isRewardAdLoaded(true);
                adInterface.rewardAdStatus(true);
            }
        };
        rewardedAd.setListener(listener);
        rewardedAd.loadAd();
    }

    //* Loader alert dialog *//
    public static AlertDialog createLoaderAlertDialog(Activity activity) {
        //Custom layout set
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.ad_dilog_loader, null);

        //Alert dialog
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(view)
                .setCancelable(false)
                .create();

        alertDialog.setCancelable(false);

        alertDialog.show();
        return alertDialog;
    }


    // Load Native Ad //
    public static MaxNativeAdLoader nativeAdLoader;
    public static MaxAd nativeAd = null;

    public static void loadNativeAd(FrameLayout frameLayout, Context context, String adId) {
        nativeAdLoader = new MaxNativeAdLoader(adId, context);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if (nativeAd != null) {
                    nativeAdLoader.destroy(nativeAd);
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                frameLayout.removeAllViews();
                frameLayout.addView(nativeAdView);
                frameLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                // We recommend retrying with exponentially higher delays up to a maximum delay
                frameLayout.setVisibility(View.GONE);
            }


        });

        nativeAdLoader.loadAd();
    }

}
