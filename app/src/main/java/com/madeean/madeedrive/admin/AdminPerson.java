package com.madeean.madeedrive.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.madeean.madeedrive.R;
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.auth.Login;
import com.madeean.madeedrive.model.ModelLogout;
import com.madeean.madeedrive.user.UserPerson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPerson extends AppCompatActivity {
    String tokenSP,nameSP,emailSP;
    TextView tv_nama_admin_person,tv_email_admin_person;
    Button btn_logout_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_person);

        SharedPreferences sh = getSharedPreferences("MadeeDrive", Context.MODE_PRIVATE);
        tokenSP = sh.getString("token", "");
        nameSP = sh.getString("name", "");
        emailSP = sh.getString("email", "");

        tv_email_admin_person = findViewById(R.id.tv_email_admin_person);
        tv_nama_admin_person = findViewById(R.id.tv_nama_admin_person);
        btn_logout_admin = findViewById(R.id.btn_logout_admin);

        tv_nama_admin_person.setText(nameSP);
        tv_email_admin_person.setText(emailSP);

        btn_logout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutHandle(tokenSP);
            }
        });


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_admin);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.person);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), AdminHome.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.plus:
                        startActivity(new Intent(getApplicationContext(), AdminAdd.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.person:


                        return true;
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(), AdminUpload.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.konfirmasi:
                        startActivity(new Intent(getApplicationContext(), AdminKonfirmasi.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void logoutHandle(String tokenSP) {
        String token = "Bearer "+tokenSP;
        ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);
        Call<ModelLogout> tampildata = api.logout(token);
        tampildata.enqueue(new Callback<ModelLogout>() {
            @Override
            public void onResponse(Call<ModelLogout> call, Response<ModelLogout> response) {
                if(response.isSuccessful()){
                    SharedPreferences sh = getSharedPreferences("MadeeDrive", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(AdminPerson.this, "Berhasil logout", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminPerson.this, Login.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AdminPerson.this, "Gagal logout", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelLogout> call, Throwable t) {
                Toast.makeText(AdminPerson.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();

            }
        });
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "tekan kembali dua kali, jika ingin menutup aplikasi", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}