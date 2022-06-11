package com.bas.card.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bas.card.model.AdsSubModel;
import com.bas.card.repository.AdsRepository;
import com.bas.card.repository.Responce;


import java.util.List;

public class AdsViewModel extends ViewModel {
    private AdsRepository adsRepository;

    public AdsViewModel(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    public LiveData<Responce<List<AdsSubModel>>> liveData() {

        adsRepository.getAdsData();
        return adsRepository.adsLiveData();
    }
}
