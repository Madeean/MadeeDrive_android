package com.madeean.madeedrive.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.madeean.madeedrive.R;
import com.madeean.madeedrive.belumlogin.AdapterDataBelumLogin;

import java.util.ArrayList;
import java.util.List;

public class UserHome extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterDataUserHome adapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

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
        List<String> listData = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            listData.add("Nama "+i);
        }
        adapterData = new AdapterDataUserHome(this, listData);
        recyclerView.setAdapter(adapterData);
        adapterData.notifyDataSetChanged();



    }
}