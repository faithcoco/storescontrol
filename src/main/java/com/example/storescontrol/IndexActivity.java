package com.example.storescontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.storescontrol.bean.LoginBean;
import com.example.storescontrol.databinding.ActivityIndexBinding;
import com.google.gson.Gson;

public class IndexActivity extends BaseActivity {
  RecyclerView recyclerView;
  FunctionAdapter functionAdapter;

    TextView titleTv;
    Button buttonexit;
    LoginBean userinfoBean;
    ActivityIndexBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=DataBindingUtil.setContentView(this,R.layout.activity_index);
        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);

        String data=sharedPreferences.getString("userinfo","");

        if(!data.equals("")){
            userinfoBean=new Gson().fromJson(data,LoginBean.class);
        }
        binding.setUser(userinfoBean);

        titleTv=binding.getRoot().findViewById(R.id.tv_title);
         titleTv.setText("首页");



        functionAdapter=new FunctionAdapter();
        recyclerView=findViewById(R.id.rv_function);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(functionAdapter);
       binding.bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IndexActivity.this,LoginActivity.class));
                IndexActivity.this.finish();

            }
        });



    }


    class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.VH>{

        @NonNull
        @Override
        public FunctionAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v=getLayoutInflater().inflate(R.layout.item_funcation,viewGroup,false);
            return new VH(v);


        }

        @Override
        public void onBindViewHolder(@NonNull  FunctionAdapter.VH vh, final int i) {
           vh.funcationButton.setText(userinfoBean.getData().get(i).getMenuname());
           vh.funcationButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(IndexActivity.this,PutDetailActivtity.class);
                   intent.putExtra("menuname",userinfoBean.getData().get(i).getMenuname());
                   startActivity(intent);
               }
           });
            Drawable drawableLeft;
            if(userinfoBean.getData().get(i).getMenuname().equals("采购入库")){
                drawableLeft = IndexActivity.this.getResources().getDrawable(
                        R.mipmap.ic_cgrk);
            }else if(userinfoBean.getData().get(i).getMenuname().equals("调拨入库")){
                drawableLeft = IndexActivity.this.getResources().getDrawable(
                        R.mipmap.ic_dbrk);
            }else if(userinfoBean.getData().get(i).getMenuname().equals("成品入库")){
                drawableLeft = IndexActivity.this.getResources().getDrawable(
                        R.mipmap.ic_cprk);
            }else {
                drawableLeft = IndexActivity.this.getResources().getDrawable(
                        R.mipmap.ic_put);
            }
            vh.funcationButton.setCompoundDrawablesWithIntrinsicBounds(null,
                    drawableLeft, null, null);


        }

        @Override
        public int getItemCount() {
            return userinfoBean.getData().size();
        }
        class  VH extends RecyclerView.ViewHolder{
            Button funcationButton;
            public VH(@NonNull View itemView) {
                super(itemView);
                funcationButton=itemView.findViewById(R.id.b_funcation);
            }
        }
    }
}
