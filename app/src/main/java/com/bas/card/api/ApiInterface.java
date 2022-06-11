package com.bas.card.api;

import com.bas.card.model.AdsModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface
ApiInterface {

    @Multipart
    @POST("app_wise_ads_data")
    Call<AdsModel> getModelData(@Part("app_id") RequestBody app_id);

}
