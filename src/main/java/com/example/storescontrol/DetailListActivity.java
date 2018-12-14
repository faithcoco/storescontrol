package com.example.storescontrol;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storescontrol.Url.Request;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.bean.DetailsBean;
import com.example.storescontrol.bean.TROutBywhcodeBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailListActivity extends BaseActivity {
    RecyclerView recyclerView;
    private FunctionAdapter functionAdapter;
    Button buttonSubmit;
    TextView textViewTotal;
    DetailsBean detailsBean=new DetailsBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
       Untils.initTitle(getIntent().getStringExtra("menuname"),this);
        recyclerView=findViewById(R.id.rv_list);
        buttonSubmit=findViewById(R.id.b_submit);
        textViewTotal=findViewById(R.id.tv_total);
        getData();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    private void checkData() {

        JSONObject jsonObject=new JSONObject();
        try {
            if(getIntent().getStringExtra("menuname").equals("调拨入库")){
                jsonObject.put("methodname","CheckTRInByccode");
            }else   if(getIntent().getStringExtra("menuname").equals("调拨出库")){
                jsonObject.put("methodname","CheckTROutByccode");
            } if(getIntent().getStringExtra("menuname").equals("材料出库")){
                jsonObject.put("methodname","CheckMaterialOutByccode");
            }
            jsonObject.put("usercode",usercode);
            jsonObject.put("id",getIntent().getStringExtra("id"));
            jsonObject.put("acccode",acccode);
            jsonObject.put("ccode",getIntent().getStringExtra("ccode"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);

        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.code()==200) {
                        Toast.makeText(DetailListActivity.this,response.body().string(),Toast.LENGTH_LONG).show();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }

    private void getData() {

        JSONObject jsonObject=new JSONObject();
        try {
            if(getIntent().getStringExtra("menuname").equals("调拨入库")){
                jsonObject.put("methodname","getTRInDetailsByccode");
            }else   if(getIntent().getStringExtra("menuname").equals("调拨出库")){
                jsonObject.put("methodname","getTROutDetailsByccode");
            } if(getIntent().getStringExtra("menuname").equals("材料出库")){
                jsonObject.put("methodname","getMaterialOutDetailsByccode");
            }
              jsonObject.put("usercode",usercode);
              jsonObject.put("id",getIntent().getStringExtra("id"));
              jsonObject.put("acccode",acccode);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);

        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.code()==200) {
                        detailsBean=new Gson().fromJson(response.body().string(),DetailsBean.class);
                        functionAdapter=new FunctionAdapter(detailsBean.getData());
                        recyclerView.setLayoutManager(new LinearLayoutManager(DetailListActivity.this));
                        recyclerView.addItemDecoration(new DividerItemDecoration(DetailListActivity.this,DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(functionAdapter);
                        textViewTotal.setText("总计："+detailsBean.getData().size()+"条");


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
            View v=getLayoutInflater().inflate(R.layout.item_detail_list,viewGroup,false);
            return new FunctionAdapter.VH(v);


        }

        private List<DetailsBean.Data> mDatas;
        public FunctionAdapter(List<DetailsBean.Data> data) {
            this.mDatas = data;
        }

        @Override
        public void onBindViewHolder(@NonNull FunctionAdapter.VH vh, final int i) {
            vh.textViewiQuantity.setText(mDatas.get(i).getIQuantity());
            vh.textViewDetails.setText(mDatas.get(i).getCInvCode()+mDatas.get(i).getCInvStd());
            vh.textViewcposition.setText(mDatas.get(i).getCposition());
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
           TextView textViewcposition,textViewDetails,textViewiQuantity;
            LinearLayout linearLayout;
            public VH(@NonNull View itemView) {
                super(itemView);
                linearLayout=itemView.findViewById(R.id.l_input);
               textViewcposition=itemView.findViewById(R.id.tv_cposition);
               textViewDetails=itemView.findViewById(R.id.tv_details);
               textViewiQuantity=itemView.findViewById(R.id.tv_iQuantity);


            }
        }
    }
}
