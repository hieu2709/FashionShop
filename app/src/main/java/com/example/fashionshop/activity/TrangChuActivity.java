package com.example.fashionshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.fashionshop.R;
import com.example.fashionshop.dataLocal.DataLocalManager;
import com.example.fashionshop.fragment.HomeFragment;
import com.example.fashionshop.fragment.ShoppingCartFragment;
import com.example.fashionshop.fragment.UserProfileFragment;
import com.example.fashionshop.object.SanPham;
import com.example.fashionshop.object.SanPhamCart;
import com.example.fashionshop.object.TaiKhoan;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TrangChuActivity extends AppCompatActivity {
    private MeowBottomNavigation bottomNavigation;
    private TextView btnlogin,tvUsername,tvDangXuat;
    private boolean isLogin= false;
    private boolean islogined=false;
    private TaiKhoan tk;
    private Bundle keySearch;
    private final int RESULT_LOGIN_RETURN=1;
    private final int RESULT_SEARCH_RETURN=2;
    private final int RESULT_MUAHANG_RETURN=100;
    private final int RESULT_THEM_VAO_GIO_RETURN=200;
    private ArrayList<SanPhamCart> listSanPhamCart;
    private ActivityResultLauncher<Intent> mactivityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        init();
        addMenuIcon();
        islogined= DataLocalManager.getIsLogined();
        if(islogined){
            tk= DataLocalManager.getTaiKhoan();
            if(tk!=null){
                isLogin=true;
            }
            if(isLogin){
//                bottomNavigation.show(1,true);
                countSanPhamCart(tk.getTentaikhoan());
                btnlogin.setVisibility(View.GONE);
                tvUsername.setText(tk.getTennguoidung());
                tvUsername.setVisibility(View.VISIBLE);
                tvDangXuat.setVisibility(View.VISIBLE);

            } else {
                btnlogin.setVisibility(View.VISIBLE);
                tvUsername.setVisibility(View.GONE);
                tvDangXuat.setVisibility(View.GONE);
            }
        }
        mactivityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==RESULT_LOGIN_RETURN){
                            Intent intentTaiKhoan=result.getData();
                            tk= (TaiKhoan) intentTaiKhoan.getSerializableExtra("account");
//                            tk= DataLocalManager.getTaiKhoan();
                            if(tk!=null){
                                isLogin=true;
                            }
                            if(isLogin){
                                bottomNavigation.show(1,true);
                                countSanPhamCart(tk.getTentaikhoan());
                                btnlogin.setVisibility(View.GONE);
                                tvUsername.setText(tk.getTennguoidung());
                                tvUsername.setVisibility(View.VISIBLE);
                                tvDangXuat.setVisibility(View.VISIBLE);

                            } else {
                                btnlogin.setVisibility(View.VISIBLE);
                                tvUsername.setVisibility(View.GONE);
                                tvDangXuat.setVisibility(View.GONE);
                            }

                        }
                        if(result.getResultCode()==RESULT_SEARCH_RETURN){
                            Intent intentSearch=result.getData();
                            keySearch=intentSearch.getBundleExtra("bundle");

                        }
                        if(result.getResultCode()==RESULT_MUAHANG_RETURN){
                            bottomNavigation.show(2,true);
                           Intent intentMuaHang=result.getData();
                            SanPham sp= (SanPham) intentMuaHang.getSerializableExtra("sanphammua");
                            String soluongmua=intentMuaHang.getStringExtra("soluongmua");
                            int id=sp.getId();
                            String tensp=sp.getTensp();
                            int gia=sp.getGia();
                            String hinhanh=sp.getHinhanh();
                            int soluong=sp.getSoluong();
                            if(tk!=null){
                                String tentaikhoan= tk.getTentaikhoan();

                                SanPhamCart spCart= new SanPhamCart();
                                spCart.setId(id);
                                spCart.setGia(gia);
                                spCart.setHinhanh(hinhanh);
                                spCart.setSoluong(Integer.parseInt(soluongmua));
                                spCart.setTensp(tensp);
                                spCart.setTentaikhoan(tentaikhoan);
                                if(soluong>Integer.parseInt(soluongmua)){
                                    addSanPhamToCart(spCart);
//                                    soluong=soluong-Integer.parseInt(soluongmua);
//                                    sp.setSoluong(soluong);
//                                    updateSanPham(sp,soluong);
                                } else {
                                    Toast.makeText(TrangChuActivity.this, "Thêm sản phẩm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }

                        if(result.getResultCode()==RESULT_THEM_VAO_GIO_RETURN){
                            bottomNavigation.show(1,true);
                            Intent intentThemvaogio=result.getData();
                            SanPham sp= (SanPham) intentThemvaogio.getSerializableExtra("sanphammua");
                            String soluongmua=intentThemvaogio.getStringExtra("soluongmua");
                            int id=sp.getId();
                            String tensp=sp.getTensp();
                            int gia=sp.getGia();
                            String hinhanh=sp.getHinhanh();
                            int soluong=sp.getSoluong();
                            if(tk!=null){
                                String tentaikhoan= tk.getTentaikhoan();

                                SanPhamCart spCart= new SanPhamCart();
                                spCart.setId(id);
                                spCart.setGia(gia);
                                spCart.setHinhanh(hinhanh);
                                spCart.setSoluong(Integer.parseInt(soluongmua));
                                spCart.setTensp(tensp);
                                spCart.setTentaikhoan(tentaikhoan);
                                if(soluong>Integer.parseInt(soluongmua)){
                                    addSanPhamToCart(spCart);
//                                    soluong=soluong-Integer.parseInt(soluongmua);
//                                    sp.setSoluong(soluong);
//                                    updateSanPham(sp,soluong);
                                } else {
                                    Toast.makeText(TrangChuActivity.this, "Thêm sản phẩm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }

                    }
                });


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()){
                    case 1:
                        fragment= new HomeFragment();
                        break;
                    case 2:
                        fragment=new ShoppingCartFragment();
                        break;
                    case 3:
                        fragment=new UserProfileFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });
        bottomNavigation.show(1,true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });

        btnlogin.setOnClickListener(view -> {
            Intent intentLogin= new Intent(this, LoginActivity.class);
            mactivityResultLauncher.launch(intentLogin);
        });

        tvDangXuat.setOnClickListener(view -> {
            DataLocalManager.clearLogin();
            Intent intentLogout= new Intent(this, TrangChuActivity.class);
            tk=null;
            intentLogout.putExtra("account",tk);
            startActivity(intentLogout);
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }

    private void init(){
        bottomNavigation=findViewById(R.id.bottom_navigation);
        btnlogin=findViewById(R.id.btnlogin);
        tvUsername=findViewById(R.id.tvUsername);
        tvDangXuat=findViewById(R.id.tvDangXuat);
    }
    private void addMenuIcon(){
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_shopping_cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_user_profile));
    }

    public ActivityResultLauncher<Intent> getMactivityResultLauncher() {
        return mactivityResultLauncher;
    }

    public Bundle getKeySearch() {
        return keySearch;
    }

    public MeowBottomNavigation getBottomNavigation() {
        return bottomNavigation;
    }

    public TaiKhoan getTk() {
        return tk;
    }
    private void addSanPhamToCart(SanPhamCart sanPhamCart){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("GioHang");
        String path=String.valueOf(sanPhamCart.getId())+sanPhamCart.getTentaikhoan();
        myRef.child(path).setValue(sanPhamCart, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(TrangChuActivity.this,"Thêm sản phẩm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void countSanPhamCart(String tentk) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("GioHang");
        listSanPhamCart = new ArrayList<>();
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    SanPhamCart sp=snapshot.getValue(SanPhamCart.class);
                    if(sp!=null){
                        if(tentk.equals(String.valueOf(sp.getTentaikhoan()))){
                            listSanPhamCart.add(sp);
                        }
                    }
                    int i=listSanPhamCart.size();
                    bottomNavigation.setCount(2,String.valueOf(i));

                } catch (Exception e){
                    //Log.e("error:",e.getMessage());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
                int i=listSanPhamCart.size();
                bottomNavigation.setCount(2,String.valueOf(i));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    try {
//                        SanPhamCart sp=dataSnapshot.getValue(SanPhamCart.class);
//                        if(tentk.equals(String.valueOf(sp.getTentaikhoan()))){
//                            listSanPhamCart.add(0,sp);
//                        }
//                    } catch (Exception e){
//                        //Log.e("error:",e.getMessage());
//                    }
//                }
//                count=listSanPhamCart.size();
//                bottomNavigation.setCount(2,String.valueOf(count));
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(TrangChuActivity.this,"Load error",Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    public TextView getTvUsername() {
        return tvUsername;
    }
}