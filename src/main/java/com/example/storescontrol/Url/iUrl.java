package com.example.storescontrol.Url;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface iUrl {
     String url="http://123456789.ngrok.yungcloud.cn";
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("/Handler.ashx")
    Call<ResponseBody> getMessage(@Body RequestBody info);   // 请求体味RequestBody 类型


}
