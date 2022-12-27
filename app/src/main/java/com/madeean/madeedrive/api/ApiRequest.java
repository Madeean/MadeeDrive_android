package com.madeean.madeedrive.api;

import com.google.gson.annotations.SerializedName;
import com.madeean.madeedrive.model.ModelAuthLogin;
import com.madeean.madeedrive.model.ModelBuku;
import com.madeean.madeedrive.model.ModelIsiDataAuthRegister;
import com.madeean.madeedrive.model.ModelLogout;
import com.madeean.madeedrive.model.ModelTambahBuku;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequest {
    @GET("belum-login")
    Call<ModelBuku> getBuku();

    @FormUrlEncoded
    @POST("login")
    Call<ModelAuthLogin> login(
        @Field("email") String email,
        @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<ModelIsiDataAuthRegister> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("logout")
    Call<ModelLogout> logout(
            @Header("Authorization") String token
    );
    @GET("buku")
    Call<ModelBuku> getBukuSudahLogin(
            @Header("Authorization") String token
    );

    @GET("buku/sendiri")
    Call<ModelBuku> getBukuSendiri(
            @Header("Authorization") String token
    );

    @Multipart
    @POST("buku")
    Call<ModelTambahBuku> tambahBuku(
            @Header("Authorization") String token,
            @Part MultipartBody.Part foto_buku,
            @Part("judul") RequestBody judul,
            @Part("sinopsis") RequestBody deskripsi,
            @Part("publik") RequestBody publik
    );

}
