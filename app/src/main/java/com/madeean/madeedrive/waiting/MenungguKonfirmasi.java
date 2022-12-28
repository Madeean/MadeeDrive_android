package com.madeean.madeedrive.waiting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.madeean.madeedrive.R;
import com.madeean.madeedrive.admin.AdminHome;
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.auth.Login;
import com.madeean.madeedrive.model.ModelLogout;
import com.madeean.madeedrive.user.UserHome;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenungguKonfirmasi extends AppCompatActivity {
    Button btn_notify, btn_logout;
    ProgressBar pb_auth_waiting;
    String tokenSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menunggu_konfirmasi);
        btn_notify = findViewById(R.id.btn_notify_waiting);
        btn_logout = findViewById(R.id.btn_logout_waiting);
        pb_auth_waiting = findViewById(R.id.pb_auth_waiting);
        pb_auth_waiting.bringToFront();

        SharedPreferences sh = getSharedPreferences("MadeeDrive", Context.MODE_PRIVATE);
        tokenSP = sh.getString("token", "");



        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tokenSP.equals("") || tokenSP.isEmpty()){
                    Toast.makeText(MenungguKonfirmasi.this, "Anda belum login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenungguKonfirmasi.this, Login.class);
                    startActivity(intent);
                    finish();
                }else{
                    logoutHandle(tokenSP);
                }
            }
        });

        btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                    notifyHandle(tokenSP);
                
            }
        });

    }

    private void notifyHandle(String tokenSP) {
        ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);
        Call<ModelLogout> tampilData = api.notifyAdmin(tokenSP);
        tampilData.enqueue(new Callback<ModelLogout>() {
            @Override
            public void onResponse(Call<ModelLogout> call, Response<ModelLogout> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MenungguKonfirmasi.this, "berhasil mengirim notif", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MenungguKonfirmasi.this, "gagal mengirim notif", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelLogout> call, Throwable t) {
                Toast.makeText(MenungguKonfirmasi.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutHandle(String tokens){
        String token = "Bearer "+tokens;
        pb_auth_waiting.setVisibility(View.VISIBLE);
        ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);
        Call<ModelLogout> tampildata = api.logout(token);
        tampildata.enqueue(new Callback<ModelLogout>() {
            @Override
            public void onResponse(Call<ModelLogout> call, Response<ModelLogout> response) {
                if(response.isSuccessful()){
                    pb_auth_waiting.setVisibility(View.INVISIBLE);
                    SharedPreferences sh = getSharedPreferences("MadeeDrive", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(MenungguKonfirmasi.this, "Berhasil logout", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MenungguKonfirmasi.this, Login.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MenungguKonfirmasi.this, "Gagal logout", Toast.LENGTH_SHORT).show();
                    pb_auth_waiting.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ModelLogout> call, Throwable t) {
                Toast.makeText(MenungguKonfirmasi.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pb_auth_waiting.setVisibility(View.INVISIBLE);

            }
        });
    }
}