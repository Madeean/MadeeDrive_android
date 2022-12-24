package com.madeean.madeedrive.waiting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.madeean.madeedrive.R;
import com.madeean.madeedrive.auth.Login;
import com.madeean.madeedrive.user.UserHome;

public class MenungguKonfirmasi extends AppCompatActivity {
    Button btn_notify, btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menunggu_konfirmasi);
        btn_notify = findViewById(R.id.btn_notify_waiting);
        btn_logout = findViewById(R.id.btn_logout_waiting);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenungguKonfirmasi.this, Login.class);
                startActivity(intent);
            }
        });

        btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenungguKonfirmasi.this, UserHome.class);
                startActivity(intent);
                finish();
            }
        });

    }
}