package com.example.storescontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storescontrol.Url.Request;
import com.example.storescontrol.bean.LoginBean;
import com.example.storescontrol.Url.iUrl;
import com.example.storescontrol.databinding.ActivityLoginBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {
    TextView titleTv;
    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityLoginBinding=DataBindingUtil.setContentView(this,R.layout.activity_login);

        titleTv=activityLoginBinding.getRoot().findViewById(R.id.tv_title);
        titleTv.setText("掌上管仓");

        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);

        activityLoginBinding.cbRemember.setChecked(sharedPreferences.getBoolean("isChecked",true));
        activityLoginBinding.etUsername.setText(sharedPreferences.getString("user",""));

        if(activityLoginBinding.cbRemember.isChecked()){
            activityLoginBinding.etPassword.setText(sharedPreferences.getString("password",""));
        }
          activityLoginBinding.cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
                  SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                  editor.putBoolean("isChecked",activityLoginBinding.cbRemember.isChecked());
                  if (isChecked==false){
                      editor.putString("password", "");
                  }
                  editor.commit();

              }
          });

            activityLoginBinding.bSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,PortActivity.class));
                }
            });

        activityLoginBinding.bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("methodname","UserLogin");
                    jsonObject.put("usercode",activityLoginBinding.etUsername.getText().toString());
                    jsonObject.put("userpassword",activityLoginBinding.etPassword.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String obj=jsonObject.toString();
                Log.i("json object",obj);
                SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
                if(sharedPreferences.getString("port","").equals("")){
                         Request.URL=Request.BASEURL;
                }else {
                      Request.URL=sharedPreferences.getString("port","");
                }
                Log.i("url--->",Request.URL);
                Retrofit retrofit=new Retrofit.Builder().baseUrl(Request.URL).build();
                RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
                iUrl login = retrofit.create(iUrl.class);
                retrofit2.Call<ResponseBody> data = login.getMessage(body);
                data.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            dialog.dismiss();
                            if(response.code()==200) {

                                LoginBean resultBean=new Gson().fromJson(response.body().string(),LoginBean.class);
                                if(resultBean.getResultcode().equals("200")) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                                    editor.putString("usercode",resultBean.getUsercode());
                                    editor.putString("user",activityLoginBinding.etUsername.getText().toString());
                                    editor.putString("password",activityLoginBinding.etPassword.getText().toString()).commit();
                                    editor.putBoolean("isChecked",activityLoginBinding.cbRemember.isChecked());
                                    editor.putString("userinfo",new Gson().toJson(resultBean));
                                    editor.commit();

                                    acccode=resultBean.getAcccode();
                                    usercode=resultBean.getUsercode();

                                    startActivity(new Intent(LoginActivity.this,IndexActivity.class));
                                    LoginActivity.this.finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,resultBean.getResultMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                    } });
            }
        });


    }

}
