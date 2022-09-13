package com.example.fashionshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionshop.R;
import com.example.fashionshop.object.SanPham;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private TextView btnQuayLai;
    private EditText edTenSP,edMin,edMax;
    private ArrayList<SanPham> listSPCustom;
    private String ten;
    private String min,max;
    private Button btnTimKiem;
    private final static int RESULT_SEARCH=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        btnQuayLai.setOnClickListener(view -> {
            onBackPressed();
        });

//
        //if(edTenSP.getText().toString().equals(""))

        btnTimKiem.setOnClickListener(view -> {
            ten=edTenSP.getText().toString().toLowerCase().trim();
            if(edMin.getText().toString().equals("")){
                min="0";
            }else {
                min=edMin.getText().toString();
            }
            if(edMax.getText().toString().equals("")){
                max="1000000000";
            }else {
                max=edMax.getText().toString();
            }
            Intent intentSearch=new Intent(this,TrangChuActivity.class);
           // intentSearch.putExtra("listSP",listSPCustom);
            Bundle bundle=new Bundle();
            bundle.putString("ten",ten);
            bundle.putString("min",min);
            bundle.putString("max",max);
            intentSearch.putExtra("bundle",bundle);
            setResult(RESULT_SEARCH,intentSearch);
            finish();
        });
    }




    public void init(){
        btnQuayLai=findViewById(R.id.btnQuayLai);
        edTenSP=findViewById(R.id.edTensp);
        edMin=findViewById(R.id.edMin);
        edMax=findViewById(R.id.edMax);
        btnTimKiem=findViewById(R.id.btnTim);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}