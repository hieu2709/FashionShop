package com.example.fashionshop.activity;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fashionshop.R;
import com.example.fashionshop.adapter.LoginAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
//hieu start
public class LoginActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ImageButton btnGoogle,btnFacebook,btnTwitter,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setLayout();
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void init(){
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        btnBack=findViewById(R.id.btnBack);
    }
    private void setLayout(){
        LoginAdapter loginAdapter=new LoginAdapter(this);
        viewPager.setAdapter(loginAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){
                case 0: tab.setText("Đăng nhập");
                break;
                case 1: tab.setText("Đăng ký");
                break;
            }
        }).attach();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // position là page muốn chuyển, 0:Đăng nhập, 1:Đăng ký
    public void changePager(int position){
        tabLayout.getTabAt(position).select();
    }
    //hieu end
}