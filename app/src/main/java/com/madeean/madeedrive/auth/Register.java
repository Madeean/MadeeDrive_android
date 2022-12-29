package com.madeean.madeedrive.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.madeean.madeedrive.R;
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.belumlogin.BelumLogin;
import com.madeean.madeedrive.model.ModelIsiDataAuthRegister;

import retrofit2.Call;

public class Register extends AppCompatActivity {

    Button btn_register;
    ImageView btn_back;
    EditText et_email, et_password, et_name;
    ProgressBar pb_auth_register;
    String tokenFCM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = findViewById(R.id.btn_register_register);
        btn_back = findViewById(R.id.iv_back_register);
        et_email = findViewById(R.id.et_email_register);
        et_password = findViewById(R.id.et_password_register);
        et_name = findViewById(R.id.et_name_register);
        pb_auth_register = findViewById(R.id.pb_auth_register);
        pb_auth_register.bringToFront();


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        // Log and toast
                        tokenFCM = token;
                        System.out.println("tokenFCM: "+tokenFCM);
                    }
                });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_email.getText().toString().trim().equals("")){
                    et_email.setError("Email tidak boleh kosong");
                }else if(et_password.getText().toString().trim().equals("")){
                    et_password.setError("Password tidak boleh kosong");
                }else if(et_name.getText().toString().trim().equals("")){
                    et_name.setError("Nama tidak boleh kosong");
                }else{
                    registerHandle();
                }
            }
        });



    }

    private void registerHandle() {
        pb_auth_register.setVisibility(View.VISIBLE);
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String name = et_name.getText().toString();
        ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);
        Call<ModelIsiDataAuthRegister> tampilData = api.register(name,email,password,tokenFCM);
        tampilData.enqueue(new retrofit2.Callback<ModelIsiDataAuthRegister>() {
            @Override
            public void onResponse(Call<ModelIsiDataAuthRegister> call, retrofit2.Response<ModelIsiDataAuthRegister> response) {
                if(response.isSuccessful()){
                    pb_auth_register.setVisibility(View.INVISIBLE);
                    Toast.makeText(Register.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(Register.this, "Gagal register", Toast.LENGTH_SHORT).show();
                    pb_auth_register.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ModelIsiDataAuthRegister> call, Throwable t) {
                Toast.makeText(Register.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pb_auth_register.setVisibility(View.INVISIBLE);

            }
        });

    }
}