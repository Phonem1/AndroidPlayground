package com.anewtech.phone.androidplayground.Models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by heriz on 20/12/2017.
 * [Model]
 */

public class CountDownViewModel extends ViewModel {

    public int count = 0;

    private MutableLiveData<Integer> countRemaining ;

    public LiveData<Integer> getCountRemaining() {
        if (countRemaining == null){
            countRemaining = new MutableLiveData<>();
        }
        return countRemaining;
    }

    public void setCountRemaining(Integer integer) {
        countRemaining.setValue(integer);
    }
}
