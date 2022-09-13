package com.example.fashionshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionshop.R;

//hieu start
public class MainActivity extends AppCompatActivity {
    private ImageView imgMen,imgName1,imgName2;
    private TextView tvTitle;
//    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getByID();
        animation();
        goToLoginActivity();
//        btn=findViewById(R.id.btn);
//        btn.setOnClickListener(view -> {
//            Intent intent =new Intent(MainActivity.this,LoginActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
//            finish();
//
//        });

    }

    private void getByID(){
        imgMen=findViewById(R.id.imgMen);
        imgName1=findViewById(R.id.imgName1);
        imgName2=findViewById(R.id.imgName2);
        tvTitle=findViewById(R.id.tvtitle);
    }
    private void animation(){
        imgMen.setTranslationY(-1500);
        imgName1.setTranslationX(1000);
        imgName2.setTranslationX(1000);
        tvTitle.setTranslationX(1000);

        imgMen.setAlpha(0);
        imgName1.setAlpha(0);
        imgName2.setAlpha(0);
        tvTitle.setAlpha(0);

        imgMen.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        imgName1.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1500).start();
        imgName2.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2000).start();
        tvTitle.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2500).start();
        //hieu end

    }
    private void goToLoginActivity(){

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent =new Intent(MainActivity.this, TrangChuActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                finish();
            }
        },4000);
    }
}