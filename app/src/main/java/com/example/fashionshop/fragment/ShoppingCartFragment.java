package com.example.fashionshop.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionshop.R;
import com.example.fashionshop.activity.TrangChuActivity;
import com.example.fashionshop.adapter.SanPhamCartAdapter;
import com.example.fashionshop.object.SanPham;
import com.example.fashionshop.object.SanPhamCart;
import com.example.fashionshop.object.TaiKhoan;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingCartFragment extends Fragment {
    private View spCartView;
    private TextView tvThongbao,tvtong,tvtongtien,tvd;
    private Button btnThanhtoan;
    private RecyclerView rcSanPhamCart;
    private SanPhamCartAdapter sanPhamCartAdapter;
    private ArrayList<SanPhamCart> listSanPhamCart;
    private ArrayList<SanPham> mlistSanPham;
    private boolean cong=true;
    private TrangChuActivity trangChuActivity;
    private TaiKhoan tk;
    private int soluongmua;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        spCartView= inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        init();
        trangChuActivity=(TrangChuActivity) getActivity();
        tk=trangChuActivity.getTk();
        if(tk==null){
            rcSanPhamCart.setVisibility(View.GONE);
            tvThongbao.setVisibility(View.VISIBLE);
            btnThanhtoan.setVisibility(View.GONE);
            tvtong.setVisibility(View.GONE);
            tvtongtien.setVisibility(View.GONE);
            tvd.setVisibility(View.GONE);
        } else{
            btnThanhtoan.setVisibility(View.VISIBLE);
            rcSanPhamCart.setVisibility(View.VISIBLE);
            tvThongbao.setVisibility(View.GONE);
            String tentk=tk.getTentaikhoan();
            listSanPhamCart=getListSanPhamCart(tentk);
            sanPhamCartAdapter=new SanPhamCartAdapter(this, new SanPhamCartAdapter.IClickItemSPCart() {
                @Override
                public void onClickSP(View view, SanPhamCart spcart) {
  //                  int id = spcart.getId();

//                    SanPham sp=GetSanPham(id);
//                    Intent intentChiTiet=new Intent(trangChuActivity, ChiTietSpActivity.class);
//                    intentChiTiet.putExtra("chiTietSP", sp);
//                    trangChuActivity.getMactivityResultLauncher().launch(intentChiTiet);

                }

                @Override
                public void onClickHuy(View view, SanPhamCart spcart) {
                    xoaKhoiCart(spcart);
                }

                @Override
                public void onClickCong(SanPhamCart spCart, int soluong) {
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference myRef=database.getReference("GioHang");
                    String path=String.valueOf(spCart.getId())+spCart.getTentaikhoan()+"/soluong";
                    myRef.child(path).setValue(soluong);
                    cong=true;
                }

                @Override
                public void onClickTru(SanPhamCart spCart, int soluong) {
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference myRef=database.getReference("GioHang");
                    String path=String.valueOf(spCart.getId())+spCart.getTentaikhoan()+"/soluong";
                    myRef.child(path).setValue(soluong);
                    cong=false;
                }
            });
            btnThanhtoan.setOnClickListener(view -> {
               ArrayList<SanPhamCart> listSanPhamCartnew=getListSanPhamCart(tentk);
               mlistSanPham=getListSanPham();
                xoaTatKhoiCart(mlistSanPham,listSanPhamCartnew,tk.getTentaikhoan());

            });
            sanPhamCartAdapter.setData(listSanPhamCart);
            rcSanPhamCart.setLayoutManager(new LinearLayoutManager(getContext()));
            rcSanPhamCart.setAdapter(sanPhamCartAdapter);

        }


        return spCartView;
    }
    private void updateSanPham(SanPham sanPham,int soluong){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("SanPham");
        String path=String.valueOf(sanPham.getId());
        myRef.child(path+"/soluong").setValue(soluong);
    }
    private ArrayList<SanPham> getListSanPham() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("SanPham");
        mlistSanPham=new ArrayList<>();
        Query query=myRef.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    try {
                        SanPham sanPham= dataSnapshot.getValue(SanPham.class);
                        mlistSanPham.add(0,sanPham);
                    } catch (Exception e){
                        Log.e("error:",e.getMessage());
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Load error",Toast.LENGTH_SHORT).show();
            }
        });
        return mlistSanPham;
    }
    private ArrayList<SanPhamCart> getListSanPhamCart(String tentk) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("GioHang");
        listSanPhamCart = new ArrayList<>();
        Query query=myRef.orderByKey();
        query.addChildEventListener(new ChildEventListener() {
            int tongtien=0;
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                        SanPhamCart sp=snapshot.getValue(SanPhamCart.class);
                        if(sp!=null){
                            if(tentk.equals(String.valueOf(sp.getTentaikhoan()))){
                                listSanPhamCart.add(0,sp);
                                tongtien+=(sp.getGia())*sp.getSoluong();
                                sanPhamCartAdapter.notifyDataSetChanged();
                            }
                        }

                        if(tongtien!=0){
                            tvtongtien.setText(String.valueOf(tongtien));
                        }
                    } catch (Exception e){
                        //Log.e("error:",e.getMessage());
                    }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                SanPhamCart sp=snapshot.getValue(SanPhamCart.class);

                if(sp!=null){
                    if(tentk.equals(String.valueOf(sp.getTentaikhoan()))){
                        int tiensanpham=0;
                        tiensanpham=sp.getGia();
                        if(cong){
                            tongtien+=tiensanpham;
                        }
                        else {
                            if(sp.getSoluong()<1){
                                return;
                            } else{
                                tongtien-=tiensanpham;
                            }
                        }

 //                       sanPhamCartAdapter.notifyDataSetChanged();
                    }
                }

                if(tongtien!=0){
                    tvtongtien.setText(String.valueOf(tongtien));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                SanPhamCart spCart=snapshot.getValue(SanPhamCart.class);
                if(spCart==null){
                    return;
                }
                for(int i=0;i<listSanPhamCart.size();i++){
                    if(spCart.getId()==listSanPhamCart.get(i).getId() && spCart.getTentaikhoan().equals(listSanPhamCart.get(i).getTentaikhoan())){
                        listSanPhamCart.remove(listSanPhamCart.get(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    try {
//                        SanPhamCart sp=dataSnapshot.getValue(SanPhamCart.class);
//                           if(tentk.equals(String.valueOf(sp.getTentaikhoan()))){
//                                listSanPhamCart.add(0,sp);
//                                sanPhamCartAdapter.notifyDataSetChanged();
//                            }
//                    } catch (Exception e){
//                        //Log.e("error:",e.getMessage());
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(),"Load error",Toast.LENGTH_SHORT).show();
//            }
//        });

        return listSanPhamCart;
    }

    private  void xoaKhoiCart(SanPhamCart spCart){
        new AlertDialog.Builder(getContext())
                .setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference myRef=database.getReference("GioHang");
                        String path=String.valueOf(spCart.getId())+spCart.getTentaikhoan();
                        myRef.child(path).removeValue();

                    }
                })
                .setNegativeButton("Không",null)
                .show();
    }

    private  void xoaTatKhoiCart(ArrayList<SanPham> listsp,ArrayList<SanPhamCart> list,String tentk){
        new AlertDialog.Builder(getContext())
                .setTitle("Thông báo")
                .setMessage("Bấm xác nhận để mua hàng?")
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference myRef=database.getReference("GioHang");
                        for (SanPhamCart sp: list
                             ) {
                            if(sp.getTentaikhoan().equals(tentk)){
                                String path=String.valueOf(sp.getId())+sp.getTentaikhoan();
                                myRef.child(path).removeValue();
                            }
                        }
                        //mlistSanPham=getListSanPham();
                        for (SanPhamCart spCart: list
                        ) {
                            for (SanPham sp:listsp
                            ) {
                                if(spCart.getId()==sp.getId()){
                                    int soluongbandau=sp.getSoluong();
                                    int soluongmua=spCart.getSoluong();
                                    int soluong=soluongbandau-soluongmua;
                                    updateSanPham(sp,soluong);
                                }
                            }

                        }


                    }
                })
                .setNegativeButton("Không",null)
                .show();
    }



    public void init(){
        rcSanPhamCart=spCartView.findViewById(R.id.rcvCart);
        tvThongbao=spCartView.findViewById(R.id.tvthongbao);
        btnThanhtoan=spCartView.findViewById(R.id.btnThanhtoan);
        tvd=spCartView.findViewById(R.id.tvd);
        tvtong=spCartView.findViewById(R.id.tvtong);
        tvtongtien=spCartView.findViewById(R.id.tongtien);
    }
}