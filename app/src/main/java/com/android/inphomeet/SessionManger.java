package com.android.inphomeet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


@SuppressWarnings("SpellCheckingInspection")
public class SessionManger {

//variables
    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;
//session names

    public static  final  String SESSION_USERSESSION="userLoginSession";
    public static  final  String SESSION_REMEMBERME="rememberMe";


//users sessions variables
    private static String IS_LOGIN = "IsLoggedIn";
    public static  String KEY_FULLNAME = "FullName";
    public static String KEY_USERNAME = "Username";
    public static String KEY_GENDER = "Gender";
    public static String KEY_PHONENUMBER = "PhoneNumber";
    public static  String KEY_PASSWORD = "Password";
//remember me session variables
    private static String IS_REMEMBERME = "IsRememberMe";
    public static String KEY_SESSIONPHONENUMBER = "PhoneNumber";
    public static  String KEY_SESSIONPASSWORD = "Password";
//construsctor
    @SuppressLint("CommitPrefEdits")
    public SessionManger(Context _context,String sessionName) {
        context = _context;
        usersSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        editor = usersSession.edit();

    }


    /*
    USER LOGIN SESSION
     */
    public void createLoginSession(String FullName, String Username, String Gender, String PhoneNumber, String Password) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_FULLNAME, FullName);
        editor.putString(KEY_USERNAME, Username);
        editor.putString(KEY_GENDER, Gender);
        editor.putString(KEY_PHONENUMBER, PhoneNumber);
        editor.putString(KEY_PASSWORD, Password);

        editor.commit();
    }

    public HashMap<String, String> getUsersDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULLNAME, usersSession.getString(KEY_FULLNAME, null));
        userData.put(KEY_GENDER, usersSession.getString(KEY_GENDER, null));
        userData.put(KEY_USERNAME, usersSession.getString(KEY_USERNAME, null));
        userData.put(KEY_PHONENUMBER, usersSession.getString(KEY_PHONENUMBER, null));
        userData.put(KEY_PASSWORD, usersSession.getString(KEY_PASSWORD, null));
        return userData;
    }

    public boolean checkLogin() {
        if (usersSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

    /*
    Remember Me session functions
     */

    public void createRememberMeSession( String PhoneNumber, String Password) {
        editor.putBoolean(IS_REMEMBERME,true);

        editor.putString(KEY_PHONENUMBER, PhoneNumber);
        editor.putString(KEY_PASSWORD, Password);

        editor.commit();
    }

    public HashMap<String, String> getRememberMeDetailsFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSIONPHONENUMBER, usersSession.getString(KEY_SESSIONPHONENUMBER, null));
        userData.put(KEY_SESSIONPASSWORD, usersSession.getString(KEY_SESSIONPASSWORD, null));
        return userData;
    }

    public boolean checkRememberMe() {
        if (usersSession.getBoolean(IS_REMEMBERME, false)) {
            return true;
        } else {
            return false;
        }
    }
}
