package com.anewtech.phone.androidplayground;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.anewtech.phone.androidplayground.Models.CountDownViewModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by heriz on 20/12/2017.
 * [UI Controller]
 */

public class CountDown_Activity extends AppCompatActivity {

    CountDownViewModel mCountModel;

    ReplaySubject<Integer> observable;
    CountDownTimer countDownTimer;
    int
//    boolean isCounting = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        mCountModel = ViewModelProviders.of(this).get(CountDownViewModel.class);

            displayCount(mCountModel.countRemaining);

    }

    public void startCount(View v) {
        countingDown();
    }

    public void stopCount(View v) {
        if(!countDownTimer.equals(null)){
            countDownTimer.cancel();
        }

    }

    public void setCountTen(View v) {
        mCountModel.count = 10;
        displayCount(mCountModel.count);
    }

    public void countingDown() {
        /* ReplaySubject emits to any observer all of the items that were emitted
         * by the source Observable, regardless of when the observer subscribes.
         */
        observable = ReplaySubject.create();
        observable.subscribe(getFirstObserver());

        countDownTimer = new CountDownTimer(mCountModel.count*1005, 1000) {
            @Override
            public void onTick(long millisUntilFinish) {
//                isCounting = true;
                mCountModel.countRemaining = (int)millisUntilFinish/1000;
                observable.onNext((int)millisUntilFinish/1000);
                displayCount(mCountModel.countRemaining);
                toLog(String.valueOf(millisUntilFinish));
            }

            @Override
            public void onFinish() {
                mCountModel.countRemaining = 0;
                displayCount(mCountModel.countRemaining);
//                isCounting = false;
            }
        }.start();

    }

    public Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                toLog(" First onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                displayCount(value);
                toLog(" First onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                toLog(" First onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                toLog(" First onComplete");
            }
        };
    }

    public void displayCount(int count) {
        TextView counter = findViewById(R.id.counter_tv);
        counter.setText(String.valueOf(count));
    }



    private void toLog(String msg) {
        Log.e("CountDown lifecycle", msg);
    }
}


/**
 * Tried a class that handles logic but updated ui in that class doesn't work with ViewModel. Might need <ViewModel inst> = ViewModelProviders.of... for it to work.
 */
