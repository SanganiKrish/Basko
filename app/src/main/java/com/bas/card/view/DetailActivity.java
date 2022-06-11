package com.bas.card.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bas.card.R;
import com.bas.card.databinding.ActivityDetailBinding;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.StaticData;


public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();

        initUi();
        initClickListener();

    }


    private void initUi() {
        setUpNativeAd();

        Intent intent = getIntent();
        if (intent.hasExtra(StaticData.INTENT_KEY)) {
            String value = intent.getStringExtra(StaticData.INTENT_KEY);


            if (value.equals("card2")) {
                binding.textCard.setText(getResources().getText(R.string.text_card_two_detail));
            }
            else if (value.equals("card3")) {
                binding.nativeAdDetailActivity.setImageDrawable(getResources().getDrawable(R.drawable.native_ad_1));
                binding.textCard.setText(getResources().getText(R.string.text_card_three_detail));


            } else if (value.equals("card4")) {
                binding.textCard.setText(getResources().getText(R.string.text_card_four_detail));
                binding.nativeAdDetailActivity.setImageDrawable(getResources().getDrawable(R.drawable.native_ad_2));

            } else if (value.equals("card5")) {
                binding.textCard.setText(getResources().getText(R.string.text_card_five_detail));
                binding.nativeAdDetailActivity.setImageDrawable(getResources().getDrawable(R.drawable.native_ad_3));

            } else if (value.equals("card6")) {
                binding.textCard.setText(getResources().getText(R.string.text_card_six_detail));
                binding.nativeAdDetailActivity.setImageDrawable(getResources().getDrawable(R.drawable.native_ad_7));

            }
        }
    }

    //* Set Native Ads *//
    private void setUpNativeAd() {

        int adStatus = NetworkUtil.getSharedPreferenceStatus(context,
                StaticData.AD_SHARED_PREFERENCE,
                StaticData.NATIVE_SMALL_AD_status,
                1);
        if (adStatus == 1) {

            String adId = NetworkUtil.getSharedPreferenceData(this,
                    StaticData.AD_SHARED_PREFERENCE,
                    StaticData.NATIVE_SMALL_AD,
                    "");
            NetworkUtil.loadNativeAd(binding.detailNativeSmallAd, context, adId);
        }
    }
    private void initClickListener() {


        binding.nativeAdDetailActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtil.setCustomIntent(DetailActivity.this);

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NetworkUtil.setCustomIntent(DetailActivity.this);
    }
}