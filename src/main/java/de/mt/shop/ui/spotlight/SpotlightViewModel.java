package de.mt.shop.ui.spotlight;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpotlightViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SpotlightViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("TODO Spotlight");
    }

    public LiveData<String> getText() {
        return mText;
    }
}