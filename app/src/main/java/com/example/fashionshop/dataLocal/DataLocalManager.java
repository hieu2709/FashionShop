package com.example.fashionshop.dataLocal;

import android.content.Context;

import com.example.fashionshop.object.TaiKhoan;
import com.google.gson.Gson;

public class DataLocalManager {
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;
    private static final String PREF_OBJECT_TAIKHOAN="PREF_OBJECT_TAIKHOAN";
    private static final String PREF_IS_LOGINED="PREF_IS_LOGINED";
    public static void init(Context context){
        instance= new DataLocalManager();
        instance.mySharedPreferences=new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance(){
        if(instance==null){
            instance=new DataLocalManager();
        }
        return instance;
    }

    public static void setTaiKhoan(TaiKhoan taiKhoan){
        Gson gson=new Gson();
        String strJsonTaiKhoan=gson.toJson(taiKhoan);

        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_TAIKHOAN,strJsonTaiKhoan);
    }

    public static TaiKhoan getTaiKhoan(){
        String strJsonTaiKhoan=DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_OBJECT_TAIKHOAN);
        Gson gson=new Gson();
        TaiKhoan taiKhoan=gson.fromJson(strJsonTaiKhoan,TaiKhoan.class);
        return taiKhoan;
    }

    public static void setIsLogined(Boolean isLogined){
        DataLocalManager.getInstance().mySharedPreferences.putBooleanValue(PREF_IS_LOGINED,isLogined);
    }
    public static Boolean getIsLogined(){
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(PREF_IS_LOGINED);

    }
    public static void clearLogin(){
        DataLocalManager.getInstance().mySharedPreferences.clearData();
    }
}
