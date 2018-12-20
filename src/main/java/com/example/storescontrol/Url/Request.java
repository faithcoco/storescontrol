package com.example.storescontrol.Url;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class Request {
    //public  static String BASEURL="http://123456789.ngrok.yungcloud.cn";
   public  static String BASEURL="http://192.168.200.20:8881";

    public  static String URL;
    public static Call<ResponseBody> getRequestbody(String obj) {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(URL).build();
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        iUrl login = retrofit.create(iUrl.class);
        retrofit2.Call<ResponseBody> data = login.getMessage(body);
        return  data;
    }
}
