package com.example.babycare.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "useraccounts")
public class UserAccount {

    @PrimaryKey
    @NonNull
    private String email;

    private String password;

    private String name;
//    public UserAccount(){
//
//    }

    public UserAccount(String email, String password) {

        this.password = password;
        this.email = email;
    }

    @Ignore
    public UserAccount(String name, String email, String password) {

        this.name = name;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setValue(UserAccount userAccount) {
        this.password = userAccount.getPassword();
        this.email = userAccount.getEmail();
    }
}
