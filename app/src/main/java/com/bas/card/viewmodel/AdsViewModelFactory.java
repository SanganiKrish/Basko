package com.bas.card.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bas.card.repository.AdsRepository;

public class AdsViewModelFactory implements ViewModelProvider.Factory {

    private AdsRepository adsRepository;

    public AdsViewModelFactory(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AdsViewModel(adsRepository);
    }
}
