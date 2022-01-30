package com.innocentmasuki.quizapp.RetrofitApi;

import com.innocentmasuki.quizapp.RetrofitApi.Auth.Auth;
import com.innocentmasuki.quizapp.RetrofitApi.Auth.Register;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DataAPI {
    @FormUrlEncoded
    @POST("register.php")
    Call<Register> register( @Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("auth.php")
    Call<Auth> authenticate(@Field("email") String email, @Field("password") String password);

}
