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

    public void getUser() {
        mRepository.getAllUsers();
    }
}
