package com.madeean.madeedrive.belumlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.madeean.madeedrive.R;
import com.madeean.madeedrive.auth.Login;

import java.util.ArrayList;
import java.util.List;

public class BelumLogin extends AppCompatActivity {

    Button btnLogin;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterDataBelumLogin adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belum_login);

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
        List<String> listData = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            listData.add("Nama "+i);
        }
        adapterData = new AdapterDataBelumLogin(this, listData);
        recyclerView.setAdapter(adapterData);
        adapterData.notifyDataSetChanged();


    }
}