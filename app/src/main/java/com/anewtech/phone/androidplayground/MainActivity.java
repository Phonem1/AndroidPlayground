package com.anewtech.phone.androidplayground;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.anewtech.phone.androidplayground.models.ScoreViewModel;

public class MainActivity extends AppCompatActivity {

    ScoreViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
        displayForTeamA(mViewModel.scoreTeamA);
        displayForTeamB(mViewModel.scoreTeamB);
    }

    public void addOneForTeamA(View v){
        mViewModel.scoreTeamA = mViewModel.scoreTeamA + 1;
        displayForTeamA(mViewModel.scoreTeamA);
    }

    public void addTwoForTeamA(View v){
        mViewModel.scoreTeamA = mViewModel.scoreTeamA + 2;
        displayForTeamA(mViewModel.scoreTeamA);
    }

    public void addThreeForTeamA(View v){
        mViewModel.scoreTeamA = mViewModel.scoreTeamA + 3;
        displayForTeamA(mViewModel.scoreTeamA);
    }
    public void addOneForTeamB(View v){
        mViewModel.scoreTeamB = mViewModel.scoreTeamB + 1;
        displayForTeamB(mViewModel.scoreTeamB);
    }

    public void addTwoForTeamB(View v){
        mViewModel.scoreTeamB = mViewModel.scoreTeamB + 2;
        displayForTeamB(mViewModel.scoreTeamB);
    }

    public void addThreeForTeamB(View v){
        mViewModel.scoreTeamB = mViewModel.scoreTeamB + 3;
        displayForTeamB(mViewModel.scoreTeamB);
    }

    public void resetScore(View v){
        mViewModel.scoreTeamA = 0 ;
        mViewModel.scoreTeamB = 0 ;
        displayForTeamA(mViewModel.scoreTeamA);
        displayForTeamB(mViewModel.scoreTeamB);
    }


    public void displayForTeamA(int score) {
        TextView scoreView = findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }
    public void displayForTeamB(int score) {
        TextView scoreView = findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }
}
