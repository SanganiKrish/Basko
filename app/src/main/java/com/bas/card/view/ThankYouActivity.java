package com.bas.card.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bas.card.R;
import com.bas.card.databinding.ActivityThankYouBinding;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.StaticData;



public class ThankYouActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityThankYouBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThankYouBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();

        initUi();
        initClickEvent();

    }

    private void initUi() {
        setUpNativeAd();
    }

    private void initClickEvent() {

        binding.buttonThankYou.setOnClickListener(this);
        //  binding.thankYouNativeAd.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonThankYou:

                if (NetworkUtil.networkConnect(context)) {
                    finish();
                    moveTaskToBack(true);

                } else {
                    NetworkUtil.snackBarShow(binding.constraint, getResources().getString(R.string.turn_on_internet_message));
                }
                break;
        }
    }

    //* Set Native Ads *//
    private void setUpNativeAd() {

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.NATIVE_MEDIUM_AD_status,
                1);

        if (adStatus == 1) {
            String adId = NetworkUtil.getSharedPreferenceData(context,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.NATIVE_MEDIUM_AD,
                    "");
            NetworkUtil.loadNativeAd(binding.thankYouNativeMediumAd, context, adId);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}