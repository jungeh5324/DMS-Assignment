package com.example.facebookclone;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface getApi {
    @GET("feed")
    Call<Data> getData();
    @POST("feed")
    Call<Post> postData(@Body Post body);
    @PATCH("feed/{id}")
    Call<Post> patchData(@Path("id")Integer id,@Body Post body);
    @DELETE("feed/{id}")
    Call<Post> deleteData(@Path("id")Integer id);
}
