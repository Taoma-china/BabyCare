package com.example.babycare.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
     public LiveData<List<UserAccount>> getUserMail(UserAccount userAccount) {return userAccountDao.getUserMail(userAccount.getEmail());}

     public void updateBabyName(String babyName, String email) { new updateAsyncTask(userAccountDao).execute(babyName, email); }

     private static class updateAsyncTask extends AsyncTask<String, String, Void> {

         private UserAccountDao mAsyncTaskDao2;


         public updateAsyncTask(UserAccountDao userAccountDao) {
             mAsyncTaskDao2 = userAccountDao;
         }

         @Override
         protected Void doInBackground(String... strings) {
             mAsyncTaskDao2.updateBabyName(strings[0], strings[1]);
             return null;
         }
     }

    public void updateBabySex(String babySex, String email) { new updateBabySexAsyncTask(userAccountDao).execute(babySex, email); }

    private static class updateBabySexAsyncTask extends AsyncTask<String, String, Void> {

        private UserAccountDao mAsyncTaskDao3;


        public updateBabySexAsyncTask(UserAccountDao userAccountDao) {
            mAsyncTaskDao3= userAccountDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mAsyncTaskDao3.updateBabySex(strings[0], strings[1]);
            return null;
        }
    }
    public void updateBabyBirth(String babyName, String email) { new updateBabyBirthAsyncTask(userAccountDao).execute(babyName, email); }

    private static class updateBabyBirthAsyncTask extends AsyncTask<String, String, Void> {

        private UserAccountDao mAsyncTaskDao3;


        public updateBabyBirthAsyncTask(UserAccountDao userAccountDao) {
            mAsyncTaskDao3 = userAccountDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mAsyncTaskDao3.updateBabyBirth(strings[0], strings[1]);
            return null;
        }
    }



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

