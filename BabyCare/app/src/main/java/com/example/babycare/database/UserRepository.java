package com.example.babycare.database;

import android.app.Application;
import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class UserRepository {
     private static UserRepository ourInstance;
     private LiveData<List<UserAccount>> allUsers;
     private UserAccountDao userAccountDao;
     UserAccountDatabase db;

     public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
     public MutableLiveData<String> Password = new MutableLiveData<>();




     public static UserRepository getInstance(Context context) {
         if(ourInstance == null) {
             ourInstance = new UserRepository(context);
         }
         return ourInstance;
     }
     private UserRepository(Context context) {
         db = UserAccountDatabase.getAppDatabase(context);
         userAccountDao = db.userAccountDao();
         allUsers = userAccountDao.getUser();
     }



     public LiveData<List<UserAccount>> find(UserAccount userAccount) {return userAccountDao.getUserMailAndPass(userAccount.getEmail(),userAccount.getPassword());}

//     private static class findAsyncTask extends AsyncTask<UserAccount, LiveData, LiveData> {
//         private UserAccountDao mAsyncTaskDao1;
//
//         findAsyncTask(UserAccountDao dao) {
//             mAsyncTaskDao1 = dao;
//         }
//
//         @Override
//         protected LiveData<List<UserAccount>> doInBackground(UserAccount... params) {
//             return mAsyncTaskDao1.getUserMailAndPass(params[0].getEmail(), params[0].getPassword());
//         }
//     }
//         return userAccountDao.getUserMailAndPass(userAccount.getEmail(),userAccount.getPassword());









    public LiveData<List<UserAccount>> getAllUsers() {
         return allUsers;
    }


    public void insert(UserAccount userAccount) {new insertAsyncTask(userAccountDao).execute(userAccount);}
    private static class insertAsyncTask extends AsyncTask<UserAccount, Void, Void> {


        private UserAccountDao mAsyncTaskDao;

         insertAsyncTask(UserAccountDao dao) {
             mAsyncTaskDao = dao;
         }

         @Override
         protected Void doInBackground(final UserAccount... params) {

                 mAsyncTaskDao.insert(params[0]);

             return null;
         }

     }

//
//    public LiveData<List<UserAccount>> find(UserAccount userAccount){
//         UserAccountDao fdao;
//         fdao = db.userAccountDao();
//         LiveData<List<UserAccount>> user;
//         user = fdao.getUserMailAndPass(userAccount.getEmail(), userAccount.getPassword());
//
//         return user;
//    }




}

