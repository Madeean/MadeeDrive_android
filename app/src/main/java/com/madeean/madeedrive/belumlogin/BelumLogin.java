package com.madeean.madeedrive.belumlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.madeean.madeedrive.R;
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.auth.Login;
import com.madeean.madeedrive.model.ModelBuku;
import com.madeean.madeedrive.model.ModelIsiData;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belum_login);

        swipeRefreshLayout = findViewById(R.id.swipe_belum_login);
        pbdata = findViewById(R.id.pb_belum_login);

        btnLogin = findViewById(R.id.btn_login_belum_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BelumLogin.this, Login.class);
                startActivity(intent);

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
    protected void onResume() {
        super.onResume();
        getData();
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

                Toast.makeText(BelumLogin.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}