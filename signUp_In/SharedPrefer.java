package com.example.portal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class SharedPrefer {
    private final String SHARED_PREF="sharedPreference";
    private final String USERNAME="username";
    private final String PASSWORD="password";
    private final String FIRSTNAME="firstname";
    private final String LASTNAME="lastname";
    private final String PHONE="phone";
    private final String EMAIL="email";
    private final String GENDER="gender";
    private static SharedPrefer mInstance;
    private final Context cxt;

    public SharedPrefer(Context mCxt){
        cxt = mCxt;
    }
    public static synchronized SharedPrefer getInstance(Context context){
        if(mInstance==null){
            mInstance= new SharedPrefer(context);
        }
        return mInstance;
    }
    public void userData(User user){
        SharedPreferences sharedpreferences = cxt.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedpreferences.edit();
        edit.putString(USERNAME, user.getUsername());
        edit.putString(PASSWORD, user.getPassword());
        edit.putString(FIRSTNAME, user.getFirstname());
        edit.putString(LASTNAME, user.getLastname());
        edit.putString(PHONE, user.getPhone());
        edit.putString(EMAIL, user.getEmail());
        edit.putString(GENDER, user.getGender());
        edit.apply();
    }
    public boolean isLogged(){
        SharedPreferences sharedpreferences = cxt.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return sharedpreferences.getString(USERNAME, null) != null;

    }
    public User getUser(){
        SharedPreferences sharedpreferences = cxt.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return new User(sharedpreferences.getString(USERNAME, null),
                sharedpreferences.getString(PASSWORD, null),
                sharedpreferences.getString(FIRSTNAME, null),
                sharedpreferences.getString(LASTNAME,null),
                sharedpreferences.getString(PHONE,null),
                sharedpreferences.getString(EMAIL,null),
                sharedpreferences.getString(GENDER,null));
    }
    public void logout(){
        SharedPreferences sharedpreferences = cxt.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedpreferences.edit();
        edit.clear();
        edit.apply();
        cxt.startActivity(new Intent(cxt, Login.class));
    }
}
