package com.anewtech.phone.androidplayground;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        mCountModel = ViewModelProviders.of(this).get(CountDownViewModel.class);

        final android.arch.lifecycle.Observer<Integer> countObserver = new android.arch.lifecycle.Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                int valueC = integer;
                displayCount(valueC);
            }
        };
        mCountModel.getCountRemaining().observe(this,countObserver);
    }

    public void startCount(View v) {
        countingDown();
    }

    public void stopCount(View v) {
        if(!countDownTimer.equals(null)){
            countDownTimer.cancel();
        }

    }

    public void setCounter(View v) {
        EditText customCountDown = findViewById(R.id.customCount_et);
        String inputCD =  customCountDown.getText().toString();
        mCountModel.count = Integer.valueOf(inputCD);
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
                mCountModel.setCountRemaining((int) (millisUntilFinish/1000));
                observable.onNext(mCountModel.getCountRemaining().getValue());
//                displayCount(mCountModel.countRemaining);
                toLog(String.valueOf(millisUntilFinish));
            }

            @Override
            public void onFinish() {
                mCountModel.count = 0;
                mCountModel.setCountRemaining(0);
                displayCount(mCountModel.getCountRemaining().getValue());
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
 * View Model only save state of variable, doesn't call function. --(tested using)--> screen rotation
 */
