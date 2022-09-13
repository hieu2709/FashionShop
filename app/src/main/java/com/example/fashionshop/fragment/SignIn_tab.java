package com.example.fashionshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fashionshop.R;
import com.example.fashionshop.activity.LoginActivity;
import com.example.fashionshop.activity.TrangChuActivity;
import com.example.fashionshop.dataLocal.DataLocalManager;
import com.example.fashionshop.object.TaiKhoan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SignIn_tab extends Fragment {
    private View vSignIn;
    private EditText edTaiKhoan,edMatKhau;
    private Button btnDangNhap,btnDangKy;
    private LoginActivity mloginActivity;
    private List<TaiKhoan> mlistTaiKhoan;
    private boolean check=false;
    private final static int RESULT_LOGIN=1;

    public SignIn_tab(){
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vSignIn= inflater.inflate(R.layout.fragment_sign_in_tab, container, false);
        mloginActivity= (LoginActivity) getActivity();
        init();
        mlistTaiKhoan=getListTaiKhoan();
        btnDangKy.setOnClickListener(view -> {
            //click vào đăng kí thì chuyển đến trang đăng ký có position là 1(0:Đăng nhập)
            mloginActivity.changePager(1);
        });
        btnDangNhap.setOnClickListener(view -> {
            if(checkData()){
                Toast.makeText(getActivity(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(),"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
            }
        });

        return vSignIn;
    }

    private void chuyenToiTrangchu(TaiKhoan acc) {
        mloginActivity=(LoginActivity) getActivity();
        DataLocalManager.setTaiKhoan(acc);
        DataLocalManager.setIsLogined(true);
        Intent intent=new Intent(mloginActivity, TrangChuActivity.class);
        intent.putExtra("account",acc);
        mloginActivity.setResult(RESULT_LOGIN,intent);
        mloginActivity.finish();
    }

    private boolean checkData(){
        for(TaiKhoan acc : mlistTaiKhoan){
            String tentk=edTaiKhoan.getText().toString();
            String mk=edMatKhau.getText().toString();
            if(tentk.equals(acc.getTentaikhoan()) && mk.equals(acc.getMatkhau())){
                chuyenToiTrangchu(acc);
               return check =true;
            }
        }
        return check;
    }

    private List<TaiKhoan> getListTaiKhoan() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("TaiKhoan");
        mlistTaiKhoan=new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        TaiKhoan tk= dataSnapshot.getValue(TaiKhoan.class);
                        mlistTaiKhoan.add(tk);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Load error",Toast.LENGTH_SHORT).show();
            }
        });
        return mlistTaiKhoan;
    }

    private void init(){
        edTaiKhoan=vSignIn.findViewById(R.id.edUser);
        edMatKhau=vSignIn.findViewById(R.id.edPassword);
        btnDangNhap=vSignIn.findViewById(R.id.btnDangNhap);
        btnDangKy=vSignIn.findViewById(R.id.btnDangKy);
    }

}