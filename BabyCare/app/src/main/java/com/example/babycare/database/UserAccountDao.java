package com.example.babycare.database;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserAccountDao {

    @Insert
    void insert(UserAccount user);

    @Update
    void update(UserAccount user);
    @Delete
    void delete(UserAccount user);

    @Query("SELECT * FROM useraccounts")
    LiveData<List<UserAccount>> getUser();

    @Query("SELECT * FROM useraccounts where email= :mail and password= :password")
    LiveData<List<UserAccount>> getUserMailAndPass(String mail, String password);

    @Query("SELECT * FROM useraccounts where email= :mail")
    LiveData<List<UserAccount>> getUserMail(String mail);

    @Query("SELECT email FROM useraccounts where email= :mail")
    LiveData<List<String>> getUserName(String mail);
}

