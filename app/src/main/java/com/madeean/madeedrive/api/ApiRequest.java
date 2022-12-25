package com.madeean.madeedrive.api;

import com.madeean.madeedrive.model.ModelBuku;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {
    @GET("belum-login")
    Call<ModelBuku> getBuku();
}
