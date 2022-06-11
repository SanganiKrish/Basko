package com.bas.card.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bas.card.api.ApiServiceRetrofit;
import com.bas.card.model.AdsModel;
import com.bas.card.model.AdsSubModel;
import com.bas.card.util.NetworkUtil;
import com.bas.card.util.StaticData;


import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AdsRepository {

    private Context context;
    private MutableLiveData<Responce<List<AdsSubModel>>> liveData = new MutableLiveData<>();

    public AdsRepository(Context context) {
        this.context = context;
    }

    public LiveData<Responce<List<AdsSubModel>>> adsLiveData() {
        return liveData;
    }

    public void getAdsData() {
        //* Data Loading *//
        liveData.postValue(Responce.loading());

        if (NetworkUtil.networkConnect(context)) {

            RequestBody body = RequestBody.create(String.valueOf(45), MediaType.parse("text/plain"));
            Call<AdsModel> call = ApiServiceRetrofit.getClient(StaticData.ADS_URL).getModelData(body);


            call.enqueue(new Callback<AdsModel>() {
                @Override
                public void onResponse(Call<AdsModel> call, retrofit2.Response<AdsModel> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            Log.d("TAG", "Response Success");
                            liveData.postValue(Responce.success(response.body().getData()));
                        } else {
                            Log.d("TAG", "Response Success");
                            liveData.postValue(Responce.fail("Request Fail , Please Try Again"));
                        }
                    } else {
                        Log.d("TAG", "Response Null");
                        liveData.postValue(Responce.fail("Request Fail,Please Try Again"));

                    }
                }

                @Override
                public void onFailure(Call<AdsModel> call, Throwable t) {
                    Log.d("TAG", "No Network");
                    Log.d("TAG", t.getLocalizedMessage());
                    liveData.postValue(Responce.internet("Turn On Internet"));
                }
            });
        }
    }
}
