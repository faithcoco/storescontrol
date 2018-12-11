package com.example.storescontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.storescontrol.Url.Untils;

public class StockcheckActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockcheck);

        Untils.initTitle(getIntent().getStringExtra("menuname"),StockcheckActivity.this);

    }
}
