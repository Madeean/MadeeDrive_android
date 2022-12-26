package com.madeean.madeedrive.api;

import com.google.gson.annotations.SerializedName;
import com.madeean.madeedrive.model.ModelAuthLogin;
import com.madeean.madeedrive.model.ModelBuku;
import com.madeean.madeedrive.model.ModelIsiDataAuthRegister;
import com.madeean.madeedrive.model.ModelLogout;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
}
