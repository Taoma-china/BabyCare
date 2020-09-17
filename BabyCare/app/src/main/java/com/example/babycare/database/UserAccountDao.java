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

    @Query("UPDATE useraccounts SET babyName = :name where email = :mail")
    void updateBabyName(String name, String mail);

    @Query("SELECT * FROM useraccounts")
    LiveData<List<UserAccount>> getUser();

    @Query("SELECT * FROM useraccounts where email= :mail and password= :password")
    LiveData<List<UserAccount>> getUserMailAndPass(String mail, String password);

    @Query("SELECT * FROM useraccounts where email= :mail")
    LiveData<List<UserAccount>> getUserMail(String mail);

    @Query("SELECT email FROM useraccounts where email= :mail")
    LiveData<List<String>> getUserName(String mail);

    @Query("UPDATE useraccounts SET babySex = :sex where email = :mail")
    void updateBabySex(String sex, String mail);

    @Query("UPDATE useraccounts SET babyBirthday = :birth where email = :mail")
    void updateBabyBirth(String birth, String mail);
}

