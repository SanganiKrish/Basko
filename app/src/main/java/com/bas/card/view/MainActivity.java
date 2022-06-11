package com.bas.card.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bas.card.R;
import com.bas.card.databinding.ActivityMainBinding;
import com.bas.card.model.AdsSubModel;
import com.bas.card.repository.AdsRepository;
import com.bas.card.repository.Responce;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.StaticData;
import com.bas.card.viewmodel.AdsViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();

        initUi();
        initClickListener();
        setAdsData();


    }


    private void initUi() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.launchLoader.setVisibility(View.GONE);
                binding.continueButton.setVisibility(View.VISIBLE);
            }
        }, 4000);
    }

    private void initClickListener() {
        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.networkConnect(context)) {
                    Intent intent = NetworkUtil.navActivity(MainActivity.this, StartActivity.class);
                    startActivity(intent);
                } else {
                    NetworkUtil.snackBarShow(binding.mainLayout, getResources().getString(R.string.turn_on_internet_message));

                }
            }
        });
    }
    //* Ads Set *//
    private void setAdsData() {

        AdsRepository adsRepository = new AdsRepository(context);
        AdsViewModel adsViewModel = new AdsViewModel(adsRepository);

        adsViewModel.liveData().observe(this, new Observer<Responce<List<AdsSubModel>>>() {
            @Override
            public void onChanged(Responce<List<AdsSubModel>> listResponse) {

                switch (listResponse.status) {

                    case LOADING:
                        Log.d("MainActivity_TAG", "Loading..");
                        break;

                    case SUCCESS:
                        Log.d("MainActivity_TAG", "Ads Data Get Success");
                        setAdsInSharePreference(listResponse.data);
                        break;

                    case FAIL:
                        Log.d("MainActivity_TAG", "Data Fail");
                        break;
                }
            }
        });
    }

    private void setAdsInSharePreference(List<AdsSubModel> list) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(StaticData.AD_SHARED_PREFERENCE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (AdsSubModel adsSubModel : list) {

            String adsName = adsSubModel.getAdsName();
            String adsId = adsSubModel.getAdsLink();
            int status = adsSubModel.getAdsStatus();

            Log.d("MYTAG", "adsName : " + adsName);
            Log.d("MYTAG", "Link : " + adsId);
            Log.d("MYTAG", "Status : " + status);

            if (adsName.equals(StaticData.APP_ID)) {
                setupAppId(adsId);
                Log.d("MainActivity_TAG", " name  : " + adsName.toString());
            } else if (adsName.equals(StaticData.INTERSTITIAL_AD)) {
                editor.putString(StaticData.INTERSTITIAL_AD, adsId);
                editor.putInt(StaticData.INTERSTITIAL_AD_status, status);
                editor.apply();
            } else if (adsName.equals(StaticData.NATIVE_MEDIUM_AD)) {
                editor.putString(StaticData.NATIVE_MEDIUM_AD, adsId);
                editor.putInt(StaticData.NATIVE_MEDIUM_AD_status, status);
                editor.apply();

            }
            else if (adsName.equals(StaticData.BANNER_AD)) {
                editor.putString(StaticData.BANNER_AD, adsId);
                editor.putInt(StaticData.BANNER_AD_status, status);
                editor.apply();

            }else if (adsName.equals(StaticData.NATIVE_SMALL_AD)) {
                editor.putString(StaticData.NATIVE_SMALL_AD, adsId);
                editor.putInt(StaticData.NATIVE_SMALL_AD_status, status);
                editor.apply();
            } else {
                editor.putString(StaticData.REWARD_AD, adsId);
                editor.putInt(StaticData.REWARD_AD_status, status);
                editor.apply();
            }
        }
    }

    //* Setup App Id *//
    private void setupAppId(String appId) {
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String myApiKey = bundle.getString("applovin.sdk.key");
            Log.d("APP_ID_TAG", "Name Found: " + myApiKey);
            ai.metaData.putString("applovin.sdk.key", appId);//you can replace your key APPLICATION_ID here
            String ApiKey = bundle.getString("applovin.sdk.key");
            Log.d("APP_ID_TAG", "ReNamed Found: " + ApiKey);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("APP_ID_TAG", "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e("APP_ID_TAG", "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
    }
}