package com.example.storescontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.storescontrol.Url.Request;

public class PortActivity extends BaseActivity{
    TextView titleTv;
    private Button buttonok;
    private EditText editText;
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port);
        titleTv=findViewById(R.id.tv_title);
        titleTv.setText("设置");
        buttonok=findViewById(R.id.b_ok);
        editText=findViewById(R.id.et_port);
        imageButton=findViewById(R.id.iv_return);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!editText.getText().toString().isEmpty()){
                   Request.URL=editText.getText().toString();
               }
            }
        });
    }
}
