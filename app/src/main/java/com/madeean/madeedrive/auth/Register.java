package com.madeean.madeedrive.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.madeean.madeedrive.R;

public class Register extends AppCompatActivity {

    Button btn_register;
    ImageView btn_back;
    EditText et_email, et_password, et_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = findViewById(R.id.btn_register_register);
        btn_back = findViewById(R.id.iv_back_register);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_name = findViewById(R.id.et_name_register);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

    }
}