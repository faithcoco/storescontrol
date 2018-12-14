package com.example.storescontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.storescontrol.Url.Request;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.bean.ArrivalHeadBean;
import com.example.storescontrol.bean.DispatchBean;
import com.example.storescontrol.bean.TROutBywhcodeBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 销售出库
 */
public class DispatchActivity extends BaseActivity {
    RecyclerView recyclerView;
    private FunctionAdapter functionAdapter;
    DispatchBean dispatchBean=new DispatchBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v=getLayoutInflater().inflate(R.layout.activity_production_list,null,false);
        setContentView(v);
        Untils.initTitle(getIntent().getStringExtra("menuname"),this);
        recyclerView=findViewById(R.id.rv_list);

        getData();
    }
    private void getData() {

        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("methodname","getDispatchBywhcode");
            jsonObject.put("usercode",usercode);
            jsonObject.put("cwhcode","");
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

                        dispatchBean=new Gson().fromJson(response.body().string(),DispatchBean.class);
                        functionAdapter=new FunctionAdapter(dispatchBean.getData());
                        recyclerView.setLayoutManager(new LinearLayoutManager(DispatchActivity.this));
                        recyclerView.addItemDecoration(new DividerItemDecoration(DispatchActivity.this,DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(functionAdapter);



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
            View v=getLayoutInflater().inflate(R.layout.item_production_list,viewGroup,false);
            return new FunctionAdapter.VH(v);


        }

        private List<DispatchBean.Data> mDatas;
        public FunctionAdapter(List<DispatchBean.Data> data) {
            this.mDatas = data;
        }

        @Override
        public void onBindViewHolder(@NonNull  FunctionAdapter.VH vh,final int i) {
            vh.textViewcwhname.setText(mDatas.get(i).getCwhname());
            vh.textViewdate.setText(mDatas.get(i).getDdate());

            vh.textViewcTRCode.setText(mDatas.get(i).getCcode());
            vh.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(DispatchActivity.this,DispatchdetailslistActivity.class);
                    intent.putExtra("id",mDatas.get(i).getID());
                    intent.putExtra("cwhcode",mDatas.get(i).getCwhcode());
                    intent.putExtra("ccode",mDatas.get(i).getCcode());
                    intent.putExtra("menuname",getIntent().getStringExtra("menuname"));
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        class  VH extends RecyclerView.ViewHolder{
            TextView textViewcTRCode,textViewdate,textViewcwhname;
            LinearLayout linearLayout;
            public VH(@NonNull View itemView) {
                super(itemView);
                linearLayout=itemView.findViewById(R.id.l_input);
                textViewcTRCode=itemView.findViewById(R.id.tv_cTRCode);
                textViewdate=itemView.findViewById(R.id.tv_date);
                textViewcwhname=itemView.findViewById(R.id.tv_cWhName);


            }
        }
    }


}
