package com.example.storescontrol;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storescontrol.Url.iUrl;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DetailActivity extends BaseActivity{
     TextView titleTv;
    RecyclerView recyclerView;
    FunctionAdapter functionAdapter;
    private ArrayList<String> mDatas;
    ImageButton scanButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleTv=findViewById(R.id.tv_title);
        scanButton=findViewById(R.id.ib_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(DetailActivity.this)
                        // 自定义Activity，重点是这行----------------------------
                        .setCaptureActivity(ScanActivity.class)
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                        .setPrompt("请对准二维码")// 设置提示语
                        .setCameraId(0)// 选择摄像头,可使用前置或者后置
                        .setBeepEnabled(false)// 是否开启声音,扫完码之后会"哔"的一声
                        .setBarcodeImageEnabled(true)// 扫完码之后生成二维码的图片
                        .addExtra("isCposcode",0)
                        .initiateScan();// 初始化扫码



            }
        });
        titleTv.setText("入库");

        mDatas=new ArrayList<>();
        mDatas.add("采购入库");
        mDatas.add("调拨入库");
        mDatas.add("成品入库");
        mDatas.add("调拨出库");
        mDatas.add("材料出库");
        mDatas.add("销售发货");
        mDatas.add("货位调整");
        mDatas.add("库存查询");
        mDatas.add("盘点");
        functionAdapter=new FunctionAdapter();
        recyclerView=findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(functionAdapter);



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {

                String code=result.getContents();
                String  numbers=code.replace("$",",");
                List<String> list = Arrays.asList(numbers.split(","));

                getData(list.get(1));


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getData(String cinvcode) {


        cinvcode="0-1563490-1";
        Gson gson=new Gson();
        JSONObject jsonObject=new JSONObject();
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
            String acccode = sharedPreferences.getString("Acccode", "");

            jsonObject.put("methodname","getInventoryBycode");
            jsonObject.put("acccode",acccode);
            jsonObject.put("cinvcode",cinvcode);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://123456789.ngrok.yungcloud.cn/").build();
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);

        iUrl login = retrofit.create(iUrl.class);
        retrofit2.Call<ResponseBody> data = login.getMessage(body);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    if(response.code()==200) {
                        String data="{\"data\":"+response.body().string()+"}";



                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });

    }

    class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.VH>{

        @NonNull
        @Override
        public FunctionAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v=getLayoutInflater().inflate(R.layout.item_input,viewGroup,false);
            return new VH(v);


        }



        @Override
        public void onBindViewHolder(@NonNull FunctionAdapter.VH vh, final int i) {
            vh.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        class  VH extends RecyclerView.ViewHolder{
            LinearLayout linearLayout;
            public VH(@NonNull View itemView) {
                super(itemView);
                linearLayout=itemView.findViewById(R.id.l_input);
            }
        }
    }




}
