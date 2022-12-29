package com.madeean.madeedrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.madeean.madeedrive.admin.AdminHome;
import com.madeean.madeedrive.belumlogin.BelumLogin;
import com.madeean.madeedrive.user.UserHome;
import com.madeean.madeedrive.waiting.MenungguKonfirmasi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




//        set timeour 3 second
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                SharedPreferences sh = getSharedPreferences("MadeeDrive", Context.MODE_PRIVATE);
                int konfirmasi = sh.getInt("konfirmasi", 100);
                int admin = sh.getInt("admin", 100);
                String token = sh.getString("token", "");
                if(konfirmasi == 100 && admin == 100 && token.equals("")) {
                    Intent intent = new Intent(MainActivity.this, BelumLogin.class);
                    startActivity(intent);
                    finish();
                }
                if(konfirmasi == 1 && admin == 0 && !token.equals("")){
                    Intent intent = new Intent(MainActivity.this, UserHome.class);
                    startActivity(intent);
                    finish();
                }
                if(konfirmasi == 1 && admin == 1 && !token.equals("")){
                    Intent intent = new Intent(MainActivity.this, AdminHome.class);
                    startActivity(intent);
                    finish();
                }
                if(konfirmasi == 0 && admin == 0 && !token.equals("")){
                    Intent intent = new Intent(MainActivity.this, MenungguKonfirmasi.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}