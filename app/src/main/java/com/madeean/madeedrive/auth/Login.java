package com.madeean.madeedrive.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.madeean.madeedrive.R;
import com.madeean.madeedrive.admin.AdminHome;
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.belumlogin.BelumLogin;
import com.madeean.madeedrive.model.ModelAuthLogin;
import com.madeean.madeedrive.model.ModelBuku;
import com.madeean.madeedrive.model.ModelIsiDataAuthLogin;
import com.madeean.madeedrive.user.UserHome;
import com.madeean.madeedrive.waiting.MenungguKonfirmasi;

import java.sql.SQLOutput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    Button btn_login, btn_register;
    EditText et_email, et_password;
    ImageView iv_back;
    ProgressBar pb_auth_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login_auth);
        btn_register = findViewById(R.id.btn_register_auth);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        iv_back = findViewById(R.id.iv_back);
        pb_auth_login = findViewById(R.id.pb_auth_login);

        pb_auth_login.bringToFront();



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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_email.getText().toString().trim().equals("")){
                    et_email.setError("Email tidak boleh kosong");
                }else if(et_password.getText().toString().trim().equals("")){
                    et_password.setError("Password tidak boleh kosong");
                }else{
                    loginHandle();
                }
            }
        });
        

        


    }

    private void loginHandle() {
        pb_auth_login.setVisibility(View.VISIBLE);
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        ApiRequest api  = Server.konekRetrofit().create(ApiRequest.class);
        Call<ModelAuthLogin> tampilData = api.login(email, password);
        tampilData.enqueue(new Callback<ModelAuthLogin>() {
            @Override
            public void onResponse(Call<ModelAuthLogin> call, Response<ModelAuthLogin> response) {
                if(response.isSuccessful()){
                    ModelIsiDataAuthLogin data = response.body().getData();
                    SharedPreferences sharedPreferences = getSharedPreferences("MadeeDrive",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("token", data.getToken());
                    myEdit.putString("name", data.getName());
                    myEdit.putString("email", data.getEmail());
                    myEdit.putInt("id", data.getId());
                    myEdit.apply();
                    pb_auth_login.setVisibility(View.INVISIBLE);
                    if(data.getKonfirmasi() == 0){
                        Intent intent = new Intent(Login.this, MenungguKonfirmasi.class);
                        startActivity(intent);
                        finish();
                    }
                    if(data.getKonfirmasi() == 1 && data.getAdmin() == 0){
                        Intent intent = new Intent(Login.this, UserHome.class);
                        startActivity(intent);
                        finish();
                    }

                    if(data.getKonfirmasi() == 1 && data.getAdmin() == 1){
                        Intent intent = new Intent(Login.this, AdminHome.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(Login.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                    pb_auth_login.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ModelAuthLogin> call, Throwable t) {
                System.out.println("debug: " + t.getMessage());
                Toast.makeText(Login.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pb_auth_login.setVisibility(View.INVISIBLE);

            }
        });
    }
}