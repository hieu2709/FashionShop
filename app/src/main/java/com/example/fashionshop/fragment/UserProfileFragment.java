package com.example.fashionshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fashionshop.R;
import com.example.fashionshop.activity.TrangChuActivity;
import com.example.fashionshop.dataLocal.DataLocalManager;
import com.example.fashionshop.object.TaiKhoan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileFragment extends Fragment {
    private boolean isLogin = false ;
    private boolean isUpdate = false ;
    private boolean check=false;
    private TaiKhoan tk,taiKhoan ;
    private String tennguoidung, matkhau, sdt, tentaikhoan;
    private TrangChuActivity trangChuActivity ;
    private TextView tv_tenkh , tv_tentk , tv_mk , tv_sdt ,edUser , tv_candangnhap ;
    private EditText edFullname  , edPassword , edTelephonenumber ;
    private Button btn_capnhat ;
    private View vUser ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vUser = inflater.inflate(R.layout.fragment_user_profile, container, false);
        init();
        trangChuActivity = (TrangChuActivity) getActivity();
        tk = trangChuActivity.getTk() ;
//        tk= DataLocalManager.getTaiKhoan();
        if(tk!=null){
            isLogin=true;
        }
        if(isLogin){
            edUser.setText(tk.getTentaikhoan());
            display(tk.getTentaikhoan());
            btn_capnhat.setOnClickListener(view -> {
                tennguoidung = edFullname.getText().toString();
                matkhau = edPassword.getText().toString();
                sdt = edTelephonenumber.getText().toString();
                tentaikhoan = edUser.getText().toString();
                taiKhoan = new TaiKhoan(tentaikhoan, matkhau, tennguoidung, sdt);
                updateUser(taiKhoan);
            });

        } else {
            tv_tenkh.setVisibility(View.GONE);
            tv_tentk.setVisibility(View.GONE);
            tv_mk.setVisibility(View.GONE);
            tv_sdt.setVisibility(View.GONE);
            edFullname.setVisibility(View.GONE);
            edUser.setVisibility(View.GONE);
            edPassword.setVisibility(View.GONE);
            edTelephonenumber.setVisibility(View.GONE);
            tv_candangnhap.setVisibility(View.VISIBLE);
            btn_capnhat.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return vUser ;
    }

    private void display(String tentk) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("TaiKhoan");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TaiKhoan tknew= dataSnapshot.getValue(TaiKhoan.class);
                    if(tknew.getTentaikhoan().equals(tentk)){
                        edFullname.setText(tknew.getTennguoidung());
                        edPassword.setText(tknew.getMatkhau());
                        edTelephonenumber.setText(tknew.getSdt());
                        trangChuActivity.getTvUsername().setText(tknew.getTennguoidung());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Load error",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void updateUser(TaiKhoan taiKhoan){
        DataLocalManager.clearLogin();
        DataLocalManager.setTaiKhoan(taiKhoan);
        DataLocalManager.setIsLogined(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance() ;
        DatabaseReference myRef = database.getReference("TaiKhoan");
        String path = taiKhoan.getTentaikhoan() ;
        myRef.child(path).setValue(taiKhoan, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();

            }
        })  ;
    }
    private void init() {

        tv_candangnhap=vUser.findViewById(R.id.tv_candangnhap) ;
        tv_tenkh=vUser.findViewById(R.id.tv_tenkh) ;
        tv_tentk=vUser.findViewById(R.id.tv_tentk);
        tv_mk=vUser.findViewById(R.id.tv_mk) ;
        tv_sdt=vUser.findViewById(R.id.tv_sdt) ;
        edFullname = vUser.findViewById(R.id.edFullname);
        edUser=vUser.findViewById(R.id.edUser) ;
        edPassword=vUser.findViewById(R.id.edPassword)  ;
        edTelephonenumber=vUser.findViewById(R.id.edTelephonenumber) ;
        btn_capnhat=vUser.findViewById(R.id.btn_capnhat);
    }
}