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
import com.madeean.madeedrive.model.ModelIsiDataAuthLogin;
import com.madeean.madeedrive.model.ModelKonfirmasi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminKonfirmasi extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterDataAdminKonfirmasi adapterData;
    List<ModelIsiDataAuthLogin> listData;
    String tokenSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_konfirmasi);

        SharedPreferences sh = getSharedPreferences("MadeeDrive", Context.MODE_PRIVATE);
        tokenSP = sh.getString("token", "");

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation_admin);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.konfirmasi);

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
                        startActivity(new Intent(getApplicationContext(), AdminUpload.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.konfirmasi:
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.rv_admin_konfirmasi);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

//        adapterData = new AdapterDataAdminKonfirmasi(this, listData);
//        recyclerView.setAdapter(adapterData);
//        adapterData.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    public void getData(){
        ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);
        Call<ModelKonfirmasi> tampilData = api.getCekKonfirmasi(tokenSP);
        tampilData.enqueue(new Callback<ModelKonfirmasi>()  {
            @Override
            public void onResponse(Call<ModelKonfirmasi> call, retrofit2.Response<ModelKonfirmasi> response) {
                if(response.isSuccessful()){
                    listData = response.body().getData();
                    adapterData = new AdapterDataAdminKonfirmasi(AdminKonfirmasi.this, listData,tokenSP);
                    recyclerView.setAdapter(adapterData);
                    adapterData.notifyDataSetChanged();

                }else{
                    Toast.makeText(AdminKonfirmasi.this, "gagal mendapatkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelKonfirmasi> call, Throwable t) {
            }
        });
    }
}