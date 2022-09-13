package com.example.fashionshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fashionshop.R;
import com.example.fashionshop.object.SanPham;

public class ChiTietSpActivity extends AppCompatActivity {
    private ImageView imgSP,btn_them_vao_gio,btn_back;
    private TextView tv_tenSP,tv_giaSP,tv_moTa,btn_cong,btn_tru,ed_so_luong;
    private Button btn_mua_ngay;
    private final static int RESULT_MUAHANG=100;
    private final static int RESULT_THEM_VAO_GIO=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        init();

        Intent intentChitiet=getIntent();
        SanPham sp= (SanPham) intentChitiet.getSerializableExtra("chiTietSP");
        String name=sp.getTensp();
        Glide.with(this).load(sp.getHinhanh()).into(imgSP);
        tv_tenSP.setText(name);
        tv_giaSP.setText(String.valueOf(sp.getGia()));
        tv_moTa.setText(sp.getMota());
        tv_moTa.setMovementMethod(new ScrollingMovementMethod());
        btn_back.setOnClickListener(view -> {
            onBackPressed();
        });
        int so_luong_max=sp.getSoluong();
        btn_cong.setOnClickListener(view -> {
            int so_luong_cu=Integer.parseInt(ed_so_luong.getText().toString());
            ed_so_luong.setText(String.valueOf(so_luong_cu+1));
            if(Integer.parseInt(ed_so_luong.getText().toString())>so_luong_max){
                ed_so_luong.setText(String.valueOf(so_luong_max));
            }
        });
        btn_tru.setOnClickListener(view -> {
            int so_luong_cu=Integer.parseInt(ed_so_luong.getText().toString());
            ed_so_luong.setText(String.valueOf(so_luong_cu-1));
            if(Integer.parseInt(ed_so_luong.getText().toString())<1){
                ed_so_luong.setText("1");
            }
        });
        btn_mua_ngay.setOnClickListener(view -> {
            String soluongmua=ed_so_luong.getText().toString();
            Intent intentMuahang=new Intent(this, TrangChuActivity.class);
            intentMuahang.putExtra("sanphammua",sp);
            intentMuahang.putExtra("soluongmua",soluongmua);
            setResult(RESULT_MUAHANG,intentMuahang);
            finish();
        });
        btn_them_vao_gio.setOnClickListener(view -> {
            String soluongmua=ed_so_luong.getText().toString();
            Intent intentThemvaogio=new Intent(this, TrangChuActivity.class);
            intentThemvaogio.putExtra("sanphammua",sp);
            intentThemvaogio.putExtra("soluongmua",soluongmua);
            setResult(RESULT_THEM_VAO_GIO,intentThemvaogio);
            finish();
        });

    }

    private void init(){
        imgSP=findViewById(R.id.imgSP);
        btn_them_vao_gio=findViewById(R.id.btn_them_vao_gio);
        btn_back=findViewById(R.id.btn_back);
        tv_tenSP=findViewById(R.id.tv_tenSP);
        tv_giaSP=findViewById(R.id.tv_giaSP);
        tv_moTa=findViewById(R.id.tv_mota);
        btn_cong=findViewById(R.id.dau_cong);
        btn_tru=findViewById(R.id.dau_tru);
        btn_mua_ngay=findViewById(R.id.btn_muaNgay);
        ed_so_luong=findViewById(R.id.ed_soluong);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}