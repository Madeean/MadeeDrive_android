package com.madeean.madeedrive.belumlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.madeean.madeedrive.R;
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.auth.Login;
import com.madeean.madeedrive.model.ModelBuku;
import com.madeean.madeedrive.model.ModelIsiData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BelumLogin extends AppCompatActivity {

    Button btnLogin;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterDataBelumLogin adapterData;
    List<ModelIsiData> listData;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar pbdata;

    String tokenFCM;

    private static final String TAG = "BANNER_AD_TAG";

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belum_login);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Log.d(TAG, "onInitializationComplete: ");
            }
        });

//        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setMaxAdContentRating()).build());

        adView = findViewById(R.id.bannerAd_belum_login);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.d(TAG, "onAdClicked: ");
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.d(TAG, "onAdClosed: ");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, "onAdFailedToLoad: "+loadAdError.getMessage());
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.d(TAG, "onAdImpression: ");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.d(TAG, "onAdOpened: ");
            }
        });



        swipeRefreshLayout = findViewById(R.id.swipe_belum_login);
        pbdata = findViewById(R.id.pb_belum_login);

        btnLogin = findViewById(R.id.btn_login_belum_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BelumLogin.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.rv_belum_login);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getData();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

//        adapterData = new AdapterDataBelumLogin(this, listData);
//        recyclerView.setAdapter(adapterData);
//        adapterData.notifyDataSetChanged();


    }

    @Override
    protected void onPause() {
        if(adView != null){
            adView.pause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (adView != null){
            adView.resume();
        }
        super.onResume();
        getData();
    }

    @Override
    protected void onDestroy() {
        if (adView != null){
            adView.destroy();
        }
        super.onDestroy();
    }

    private void getData() {
        pbdata.setVisibility(View.VISIBLE);
        ApiRequest api  = Server.konekRetrofit().create(ApiRequest.class);
        Call<ModelBuku> tampilData = api.getBuku();
        tampilData.enqueue(new Callback<ModelBuku>() {
            @Override
            public void onResponse(Call<ModelBuku> call, Response<ModelBuku> response) {
                if(response.isSuccessful()){
                    listData  = response.body().getData();
                    adapterData = new AdapterDataBelumLogin(BelumLogin.this, listData);
                    recyclerView.setAdapter(adapterData);
                    adapterData.notifyDataSetChanged();
                    pbdata.setVisibility(View.INVISIBLE);
                }else{
                    pbdata.setVisibility(View.INVISIBLE);

                    System.out.println(response.errorBody());
                    Toast.makeText(BelumLogin.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelBuku> call, Throwable t) {
                pbdata.setVisibility(View.INVISIBLE);

                Toast.makeText(BelumLogin.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}