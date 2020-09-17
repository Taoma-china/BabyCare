package com.example.babycare.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.babycare.database.UserAccount;
import com.example.babycare.database.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository mRepository;
    private LiveData<List<UserAccount>> mAllUsers;
    private MutableLiveData<String> email;


    public UserViewModel (Application application) {
        super(application);
        mRepository = UserRepository.getInstance(application.getApplicationContext());
        mAllUsers = mRepository.getAllUsers();

    }

    public LiveData<List<UserAccount>> getmAllUsers() {
        return mAllUsers;
    }


    public void insert(UserAccount userAccount) {mRepository.insert(userAccount);}
    public LiveData<List<UserAccount>> find(UserAccount userAccount) { return mRepository.find(userAccount);}


    public LiveData<List<UserAccount>> checkUser(UserAccount userAccount) {return mRepository.getUserMail(userAccount);}


    public void getUser() {
        mRepository.getAllUsers();
    }

    public void updateBabyName(String babyName, String email) { mRepository.updateBabyName(babyName, email);}

    private final MutableLiveData<String> babyName = new MutableLiveData<>();
    public void setBabyName(String Name) {
        babyName.setValue(Name);
    }
    public LiveData<String> getBabyName() {
        return babyName;
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public String getEmail() {return email;}
    public MutableLiveData<String> getEmail() {
        if (email == null) {
            email = new MutableLiveData<String>();
        }
        return email;
    }

    public void updataBabyBirth(String babyBirth, String email) {
        mRepository.updateBabyBirth(babyBirth, email);
    }

    public void updataBabySex(String babySex, String email) {
        mRepository.updateBabySex(babySex, email);
    }
}
