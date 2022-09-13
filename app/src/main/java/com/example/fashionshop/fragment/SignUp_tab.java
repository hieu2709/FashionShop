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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fashionshop.R;
import com.example.fashionshop.activity.LoginActivity;
import com.example.fashionshop.object.TaiKhoan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignUp_tab extends Fragment {
    private LoginActivity mloginActivity ;
    private EditText edFullname , edUser ,edPassword , edTelephone ;
    private Button btnDangKy , btnDangNhap ;
    private View vDangKy;
    private TextView tv;
    private SignIn_tab signIn_tab = new SignIn_tab() ;
    private List<TaiKhoan> mlistTaiKhoan;
    public SignUp_tab(){}
    private SignUp_tab s = this ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vDangKy= inflater.inflate(R.layout.fragment_sign_up_tab, container, false);
        mloginActivity=(LoginActivity) getActivity() ;
        mlistTaiKhoan=getListTaiKhoan();

        init();

        edUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUser.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.ed_text_bg));
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tennguoidung = (edFullname.getText().toString().trim()) ;
                String tentaikhoan = edUser.getText().toString().trim() ;
                String matkhau     = edPassword.getText().toString().trim();
                String sdt         = edTelephone.getText().toString().trim();
                TaiKhoan taiKhoan = new TaiKhoan(tentaikhoan,matkhau,tennguoidung,sdt);
                boolean ktr = true ;


                for (TaiKhoan taiKhoan1 : mlistTaiKhoan)

                {

                    if(tentaikhoan.equals(taiKhoan1.getTentaikhoan())){
                        edUser.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_hong));
                        Toast.makeText(getActivity(),"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
                        ktr = false ;

                    }

                }
                if(ktr)
                { onClickAddUser(taiKhoan)  ;
                    Toast.makeText(getActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                    mloginActivity.changePager(0);
                    clearInfo();
                }
            }
        });
        btnDangNhap.setOnClickListener(view -> {
            //click vào đăng nhập thì chuyển đến trang đăng nhập có position là 0(1:Đăng Ký)
            mloginActivity.changePager(0);

        });
        return vDangKy;
    }

    private void clearInfo(){
        edFullname.setText("");
        edUser.setText("");
        edTelephone.setText("");
        edPassword.setText("");

    }
    private void onClickAddUser(TaiKhoan taiKhoan) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TaiKhoan");

        String pathObject = String.valueOf(taiKhoan.getTentaikhoan());
        myRef.child(pathObject).setValue(taiKhoan);

    }

    public List<TaiKhoan> getListTaiKhoan() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("TaiKhoan");
        List<TaiKhoan> newlist=new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TaiKhoan tk= dataSnapshot.getValue(TaiKhoan.class);
                    newlist.add(tk);



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Load error",Toast.LENGTH_SHORT).show();
            }
        });
        return newlist;
    }
    private void init(){
        edFullname = vDangKy.findViewById(R.id.edFullname) ;
        edUser = vDangKy.findViewById(R.id.edUser) ;
        edPassword=vDangKy.findViewById(R.id.edPassword) ;
        edTelephone=vDangKy.findViewById(R.id.edTelephonenumber) ;
        btnDangKy=vDangKy.findViewById(R.id.btnDangKy) ;
        btnDangNhap=vDangKy.findViewById(R.id.btnDangNhap) ;
    }
}