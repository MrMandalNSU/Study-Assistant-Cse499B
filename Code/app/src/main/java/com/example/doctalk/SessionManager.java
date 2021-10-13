package com.example.doctalk;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {


    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;


    private static  final String IS_LOGIN = "IsLoggedIn";

    public  static  final String KEY_FULLNAME = "fullname";
    public  static  final String KEY_EMAIl = "email";
    public  static  final String KEY_LICENSENUMBER = "licenseNumber";
    public  static  final String KEY_USERTYPE = "userType";
    public  static  final String KEY_PHONE = "phone";

    public SessionManager(Context _context){

        context = _context;
        usersSession = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = usersSession.edit();

    }


    public  void  CreateLoginSession (String fullname, String email, String phone, String userType, String licenseNumber){

        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_FULLNAME,fullname);
        editor.putString(KEY_EMAIl,email);
        editor.putString(KEY_PHONE,phone);
        editor.putString(KEY_USERTYPE,userType);
        editor.putString(KEY_LICENSENUMBER,licenseNumber);

        editor.commit();
    }
    public HashMap<String, String> getUserDetailFromSession(){

        HashMap<String,String> userdata = new HashMap<String, String>();

        userdata.put(KEY_FULLNAME,usersSession.getString(KEY_FULLNAME, null));
        userdata.put(KEY_EMAIl,usersSession.getString(KEY_EMAIl, null));
        userdata.put(KEY_PHONE,usersSession.getString(KEY_PHONE, null));
        userdata.put(KEY_LICENSENUMBER,usersSession.getString(KEY_LICENSENUMBER, null));
        userdata.put(KEY_USERTYPE,usersSession.getString(KEY_USERTYPE, null));

        return  userdata;

    }

    public  boolean checkLogin(){

        if (usersSession.getBoolean(IS_LOGIN, false)){

            return  true;
        }
        else {
            return false;
        }
    }

    public void  logoutUserSession(){
        editor.clear();
        editor.commit();
    }
}
