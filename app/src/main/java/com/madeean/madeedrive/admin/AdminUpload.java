package com.madeean.madeedrive.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.madeean.madeedrive.R;
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.model.ModelBuku;
import com.madeean.madeedrive.model.ModelIsiData;
import com.madeean.madeedrive.user.AdapterDataUserUpload;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AdminUpload extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterAdminUpload adapterData;

    List<ModelIsiData> listData;
    String tokenSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload);

        SharedPreferences sh = getSharedPreferences("MadeeDrive", Context.MODE_PRIVATE);
        tokenSP = sh.getString("token", "");

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_admin);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.upload);

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
                        startActivity(new Intent(getApplicationContext(), AdminPerson.class));
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.upload:
                        return true;
                    case R.id.konfirmasi:
                        startActivity(new Intent(getApplicationContext(), AdminKonfirmasi.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.rv_admin_upload);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

//        adapterData = new AdapterAdminUpload(this, listData);
//        recyclerView.setAdapter(adapterData);
//        adapterData.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);
        Call<ModelBuku> tampilData = api.getBukuSendiri(tokenSP);
        tampilData.enqueue(new retrofit2.Callback<ModelBuku>() {
            @Override
            public void onResponse(Call<ModelBuku> call, retrofit2.Response<ModelBuku> response) {
                if(response.isSuccessful()){
                    listData = response.body().getData();
                    adapterData = new AdapterAdminUpload(AdminUpload.this, listData);
                    recyclerView.setAdapter(adapterData);
                    adapterData.notifyDataSetChanged();
                }else{
                    Toast.makeText(AdminUpload.this, "gagal mendapatkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelBuku> call, Throwable t) {
                Toast.makeText(AdminUpload.this, "gagal menghubungi data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}