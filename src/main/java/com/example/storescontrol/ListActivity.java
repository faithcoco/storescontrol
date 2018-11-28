package com.example.storescontrol;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends BaseActivity {
    RecyclerView recyclerView;
    FunctionAdapter functionAdapter;
    private ArrayList<String> mDatas;
    TextView titleTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        titleTv=findViewById(R.id.tv_title);
        titleTv.setText("出库");
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
        recyclerView=findViewById(R.id.rv_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(functionAdapter);
    }
    class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.VH>{

        @NonNull
        @Override
        public FunctionAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v=getLayoutInflater().inflate(R.layout.item_output,viewGroup,false);
            return new VH(v);


        }



        @Override
        public void onBindViewHolder(@NonNull  FunctionAdapter.VH vh, final int i) {
           vh.relativeLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   startActivity(new Intent(ListActivity.this,DetailActivity.class));
               }
           });


        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        class  VH extends RecyclerView.ViewHolder{
            RelativeLayout relativeLayout;
            public VH(@NonNull View itemView) {
                super(itemView);
               relativeLayout=itemView.findViewById(R.id.rl_output);
            }
        }
    }
}
