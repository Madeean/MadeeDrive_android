package com.madeean.madeedrive.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.madeean.madeedrive.R;
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.belumlogin.AdapterDataBelumLogin;
import com.madeean.madeedrive.model.ModelBuku;
import com.madeean.madeedrive.model.ModelIsiData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHome extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterDataUserHome adapterData;
    String tokenSP;
    List<ModelIsiData> listData;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar pbdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        swipeRefreshLayout = findViewById(R.id.swipe_user_home);
        pbdata = findViewById(R.id.pb_user_home);
        pbdata.bringToFront();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        SharedPreferences sh = getSharedPreferences("MadeeDrive", Context.MODE_PRIVATE);
        tokenSP = sh.getString("token", "");

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        return true;
                    case R.id.plus:
                        startActivity(new Intent(getApplicationContext(),UserAdd.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(),UserPerson.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(),UserUpload.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.rv_user_home);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);





    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        pbdata.setVisibility(ProgressBar.VISIBLE);
        String token = "Bearer "+tokenSP;
        ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);
         Call<ModelBuku> tampildata = api.getBukuSudahLogin(token);
         tampildata.enqueue(new Callback<ModelBuku>() {
             @Override
             public void onResponse(Call<ModelBuku> call, Response<ModelBuku> response) {
                    if(response.isSuccessful()){
                        pbdata.setVisibility(ProgressBar.INVISIBLE);
                        listData = response.body().getData();
                        adapterData = new AdapterDataUserHome(UserHome.this, listData);
                        recyclerView.setAdapter(adapterData);
                        adapterData.notifyDataSetChanged();
                    }else{
                        Toast.makeText(UserHome.this, "gagal mendapatkan data", Toast.LENGTH_SHORT).show();
                        pbdata.setVisibility(ProgressBar.INVISIBLE);

                    }
             }

             @Override
             public void onFailure(Call<ModelBuku> call, Throwable t) {
                 System.out.println("ERROR : "+t.getMessage());
                    Toast.makeText(UserHome.this, "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                 pbdata.setVisibility(ProgressBar.INVISIBLE);

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