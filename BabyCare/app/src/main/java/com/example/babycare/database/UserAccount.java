package com.example.babycare.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "useraccounts")
public class UserAccount {

    @PrimaryKey
    @NonNull
    private String email;

    private String password;

    private String name;

    private String re_password;

    private String babyName;

    private String babyBirthday;

    private String babySex;
//    public UserAccount(){
//
//    }

//    @Ignore
//    public UserAccount(String email, String password) {
//
//        this.password = password;
//        this.email = email;
//    }
    @Ignore
    public UserAccount(String name, String email, String password, String re_password) {

        this.re_password = re_password;
        this.name = name;
        this.password = password;
        this.email = email;

    }


    public UserAccount(String name, String email, String password, String re_password, String babyName, String babyBirthday, String babySex) {

        this.re_password = re_password;
        this.name = name;
        this.password = password;
        this.email = email;
        this.babyName = babyName;
        this.babyBirthday = babyBirthday;
        this.babySex = babySex;
    }

    @Ignore
    public UserAccount(String email) {
        this.email = email;

    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                "email='" + email + '\'' +
                "password='" + password + '\'' +
                "re_password='" + re_password + '\''+
                "baby_name='" + babyName + '\'' +
                "baby_birthday='" + babyBirthday + '\'' +
                "baby_sex='" + babySex + '\'' +
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

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getRe_password() {return re_password;}
    public void setRe_password(@NonNull String re_password) {
        this.re_password = re_password;
    }

    public String getBabyName() {return babyName;}
    public void setBabyName(@NonNull String babyName) {
        this.babyName = babyName;
    }

    public String getBabyBirthday() {return babyBirthday;}
    public void setBabyBirthday(@NonNull String babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

    public String getBabySex() {return babySex;}
    public void setBabySex(@NonNull String babySex) {
        this.babySex = babySex;
    }

    public void setValue(UserAccount userAccount) {
        this.password = userAccount.getPassword();
        this.email = userAccount.getEmail();
        this.re_password = userAccount.re_password;
        this.name = userAccount.name;

        this.babySex = userAccount.getBabySex();
        this.babyName = userAccount.getBabyName();
        this.babyBirthday = userAccount.getBabyBirthday();
    }
}
