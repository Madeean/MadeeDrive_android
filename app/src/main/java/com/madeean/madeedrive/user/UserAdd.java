package com.madeean.madeedrive.user;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.madeean.madeedrive.R;
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.model.ModelTambahBuku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdd extends AppCompatActivity {
    Button btn_file_user_add, btn_upload_user_add;
    TextView tv_nama_file_user_add;
    File file;
    Switch sw_user_add;

    EditText et_nama_user_add, et_deskripsi_user_add;
    String tokenSP;
    int switches;



    private static final int FILE_SELECT_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);


        SharedPreferences sh = getSharedPreferences("MadeeDrive", Context.MODE_PRIVATE);
        tokenSP = sh.getString("token", "");

        et_nama_user_add = findViewById(R.id.et_nama_user_add);
        et_deskripsi_user_add = findViewById(R.id.et_deskripsi_user_add);
        btn_upload_user_add = findViewById(R.id.btn_upload_user_add);


        tv_nama_file_user_add = findViewById(R.id.tv_nama_file_user_add);
        sw_user_add = findViewById(R.id.sw_user_add);
        sw_user_add.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                switches = 0;
            }else {
                switches = 1;
            }
        });


        btn_file_user_add = findViewById(R.id.btn_file_user_add);
        btn_file_user_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestPermissions();


            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.plus);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), UserHome.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.plus:

                        return true;
                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(), UserPerson.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.upload:
                        startActivity(new Intent(getApplicationContext(), UserUpload.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        btn_upload_user_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_nama_user_add.getText().toString().trim().equals("")) {
                    Toast.makeText(UserAdd.this, "Nama File Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else if (et_deskripsi_user_add.getText().toString().trim().equals("")) {
                    Toast.makeText(UserAdd.this, "Deskripsi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else if (file == null) {
                    Toast.makeText(UserAdd.this, "File Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    postHandle();


                }
            }
        });
    }


    private void requestPermissions() {
        Dexter.withActivity(this)
                // below line is use to request the number of permissions which are required in our app.
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        // below is the list of permissions
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                // after adding permissions we are calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            Intent intent = new Intent(UserAdd.this, FilePickerActivity.class);
                            intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                            .setCheckPermission(true)
                            .setShowImages(true)
                            .enableImageCapture(true)
                            .setShowVideos(true)
                            .setShowAudios(true)
                            .setShowFiles(true)
                            .build());
                            startActivityForResult(intent, FILE_SELECT_CODE);
                            Toast.makeText(UserAdd.this, "All the permissions are granted..", Toast.LENGTH_SHORT).show();
                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently, we will show user a dialog message.
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        // this method is called when user grants some permission and denies some of them.
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(error -> {
                    // we are displaying a toast message for error message.
                    Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                })
                // below line is use to run the permissions on same thread and to check the permissions
                .onSameThread().check();
    }

    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(UserAdd.this);

        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions");

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel();
            // below is the intent from which we are redirecting our user.
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // this method is called when user click on negative button.
            dialog.cancel();
        });
        // below line is used to display our dialog
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case FILE_SELECT_CODE:
                ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
                //Do something with files
                file = new File(files.get(0).getPath());
                tv_nama_file_user_add.setText(file.getName());
                System.out.println("PATH "+file.getPath());
                break;
        }
    }

    private void postHandle() {
        String token = "Bearer " + tokenSP;
        String judul = et_nama_user_add.getText().toString();
        String sinopsis = et_deskripsi_user_add.getText().toString();
        int publik = switches;
        File foto_buku = file;
        ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), foto_buku);
        MultipartBody.Part body = MultipartBody.Part.createFormData("foto_buku", foto_buku.getName(), requestFile);
        RequestBody judulBody = RequestBody.create(MediaType.parse("multipart/form-data"), judul);
        RequestBody sinopsisBody = RequestBody.create(MediaType.parse("multipart/form-data"), sinopsis);
        RequestBody publikBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(publik));

        Call<ModelTambahBuku> tampilData = api.tambahBuku(token, body, judulBody, sinopsisBody, publikBody);
        tampilData.enqueue(new Callback<ModelTambahBuku>() {
            @Override
            public void onResponse(Call<ModelTambahBuku> call, Response<ModelTambahBuku> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserAdd.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserAdd.this, UserHome.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UserAdd.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelTambahBuku> call, Throwable t) {
                System.out.println("Error : " + t.getMessage());
                Toast.makeText(UserAdd.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
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

