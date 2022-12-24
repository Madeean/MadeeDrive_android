package com.madeean.madeedrive.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.madeean.madeedrive.R;
import com.madeean.madeedrive.belumlogin.BelumLogin;

public class Login extends AppCompatActivity {

    Button btn_login, btn_register;
    EditText et_email, et_password;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login_auth);
        btn_register = findViewById(R.id.btn_register_auth);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, BelumLogin.class);
                startActivity(intent);
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });


    }
}