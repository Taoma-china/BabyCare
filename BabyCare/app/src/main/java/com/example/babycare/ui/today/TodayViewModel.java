package com.example.babycare.ui.today;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodayViewModel extends ViewModel {
//    private Drawable image;

    private MutableLiveData<String> mText;
    private String email1;

    public TodayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is today fragment");



    }

    public LiveData<String> getText() {
        return mText;
    }


    private final MutableLiveData<String> babyName = new MutableLiveData<>();
    public void setBabyName(String Name) {
        babyName.setValue(Name);
    }
    public LiveData<String> getBabyName() {
        return babyName;
    }

    private MutableLiveData<String> userEmail = new MutableLiveData<>();
    public void setUserEmail(String s){
        userEmail.setValue(s);
    }
    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    private final MutableLiveData<String> feedTime = new MutableLiveData<>();
    public void setFeedTime(String time) {
        feedTime.setValue(time);
    }
    public LiveData<String> getFeedTime() {
        return feedTime;
    }

    private final MutableLiveData<String> babyDate = new MutableLiveData<>();
    public void setBabyDate(String date) {
        babyDate.setValue(date);
    }
    public LiveData<String> getBabyDate() {
        return babyDate;
    }

    private final MutableLiveData<String> feedDate = new MutableLiveData<>();
    public void setFeedDate(String time) {
        feedDate.setValue(time);
    }
    public LiveData<String> getFeedDate() {
        return feedDate;
    }


    private final MutableLiveData<String> sleepTime = new MutableLiveData<>();
    public void setSleepTime(String time) {
        sleepTime.setValue(time);
    }
    public LiveData<String> getSleepTime() {
        return sleepTime;
    }


    private final MutableLiveData<String> sleepDate = new MutableLiveData<>();
    public void setSleepDate(String time) {
        sleepDate.setValue(time);
    }
    public LiveData<String> getSleepDate() {
        return sleepDate;
    }

//
}