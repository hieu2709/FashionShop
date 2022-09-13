package com.example.fashionshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fashionshop.R;
import com.example.fashionshop.activity.ChiTietSpActivity;
import com.example.fashionshop.activity.SearchActivity;
import com.example.fashionshop.activity.TrangChuActivity;
import com.example.fashionshop.adapter.SanPhamAdapter;
import com.example.fashionshop.adapter.SliderAdapter;
import com.example.fashionshop.object.Photo;
import com.example.fashionshop.object.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {
    private boolean isSearch=false;
    private String tensp;
    private int min,max;
    private View homeView;
    private ImageButton btnSearch;
    private ViewPager2 mViewpager;
    private RadioButton rdAo,rdQuan,rdTatca;
    private CircleIndicator3 mCircleIndicator;
    private List<Photo> mlistPhoto;
    private ArrayList<SanPham> mlistSanPham;
    private ArrayList<SanPham> newListSanPham;
    private TrangChuActivity mTrangchuActivity;
    private Bundle keySearch;
    private RecyclerView mRecycleView;
    private SanPhamAdapter sanPhamAdapter;
    private boolean isLoading;
    private boolean isLastPage;
    private int totalPage=5;
    private int currentPage=1;
    private int limit;
    private Handler photoHandler=new Handler();
    private Runnable photoRunnable=new Runnable() {
        @Override
        public void run() {
            if(mViewpager.getCurrentItem()==mlistPhoto.size()-1){
                mViewpager.setCurrentItem(0);
            }else {
                mViewpager.setCurrentItem(mViewpager.getCurrentItem()+1);
            }

        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeView= inflater.inflate(R.layout.fragment_home, container, false);
        init();
        //sliderImage
        mTrangchuActivity=(TrangChuActivity)getActivity();
        mlistPhoto=getListPhoto();
        SliderAdapter sliderAdapter=new SliderAdapter(mlistPhoto);
        mViewpager.setAdapter(sliderAdapter);
        mCircleIndicator.setViewPager(mViewpager);
        mViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                photoHandler.removeCallbacks(photoRunnable);
                photoHandler.postDelayed(photoRunnable,2000);
            }
        });

        sanPhamAdapter=new SanPhamAdapter(this, new SanPhamAdapter.IClickItemSP() {
            @Override
            public void onClickSP(View view, SanPham sp) {
                Intent intentChiTiet=new Intent(mTrangchuActivity, ChiTietSpActivity.class);
                intentChiTiet.putExtra("chiTietSP", sp);
                mTrangchuActivity.getMactivityResultLauncher().launch(intentChiTiet);

            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.setAdapter(sanPhamAdapter);
       keySearch=mTrangchuActivity.getKeySearch();
        if(keySearch!=null){
            isSearch=true;
            tensp=keySearch.getString("ten");
            min=Integer.parseInt(keySearch.getString("min"));
            max=Integer.parseInt(keySearch.getString("max"));
        }
        if(isSearch) {
            ArrayList mlistSanPhamMoi=getListSanPhamSearch(tensp,min,max);
            sanPhamAdapter.setData(mlistSanPhamMoi);
            //mRecycleView.setAdapter(sanPhamAdapter);
            rdTatca.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    newListSanPham = getListSanPhamSearch(tensp,min,max);
                    sanPhamAdapter.setData(newListSanPham);
                    //mRecycleView.setAdapter(sanPhamAdapter);
                }

            });
            rdAo.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    newListSanPham = getListAo(mlistSanPhamMoi);
                    sanPhamAdapter.setData(newListSanPham);
                    //mRecycleView.setAdapter(sanPhamAdapter);
                }
            });
            rdQuan.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    newListSanPham = getListQuan(mlistSanPhamMoi);
                    sanPhamAdapter.setData(newListSanPham);
                    //mRecycleView.setAdapter(sanPhamAdapter);
                }

            });
        }else{
            limit=5;
            mlistSanPham = getListSanPham();
            sanPhamAdapter.setData(mlistSanPham);
            rdTatca.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    newListSanPham = getListSanPham();
                    sanPhamAdapter.setData(newListSanPham);
                    //mRecycleView.setAdapter(sanPhamAdapter);
                }

            });
            rdAo.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    newListSanPham = getListAo(mlistSanPham);
                    sanPhamAdapter.setData(newListSanPham);
                    //mRecycleView.setAdapter(sanPhamAdapter);
                }
            });
            rdQuan.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    newListSanPham = getListQuan(mlistSanPham);
                    sanPhamAdapter.setData(newListSanPham);
                    //mRecycleView.setAdapter(sanPhamAdapter);
                }

            });
        }


        btnSearch.setOnClickListener(view -> {
            Intent intentSearch= new Intent(mTrangchuActivity, SearchActivity.class);
            mTrangchuActivity.getMactivityResultLauncher().launch(intentSearch);
        });


        return homeView;
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
                        sanPhamAdapter.notifyDataSetChanged();
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


    private ArrayList<SanPham> getListSanPhamSearch(String tensp,int min,int max) {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("SanPham");
        newListSanPham=new ArrayList<>();
        Query query=myRef.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    try {
                        SanPham sanPham= dataSnapshot.getValue(SanPham.class);
                        if(sanPham.getTensp().toLowerCase().contains(tensp)&&sanPham.getGia()>=min&&sanPham.getGia()<=max){
                            newListSanPham.add(0,sanPham);
                        }

                        sanPhamAdapter.notifyDataSetChanged();
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
        return newListSanPham;
    }

    private ArrayList<SanPham> getListAo(ArrayList<SanPham> listSP){
        newListSanPham=new ArrayList<>();
        for (SanPham sp: listSP
             ) {
            if(sp.getLoaisp().equals("Áo")){
                newListSanPham.add(0,sp);
            }

        }
        sanPhamAdapter.notifyDataSetChanged();
//        FirebaseDatabase database=FirebaseDatabase.getInstance();
//        DatabaseReference myRef= database.getReference("SanPham");
//        mlistSanPham=new ArrayList<>();
//        //Query query=myRef.orderByChild("loaisp").equalTo("Áo");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    try {
//                        SanPham sanPham= dataSnapshot.getValue(SanPham.class);
//                        if(sanPham.getLoaisp().equals("Áo")){
//                            mlistSanPham.add(0,sanPham);
//                        }
//                        sanPhamAdapter.notifyDataSetChanged();
//                    } catch (Exception e){
//                        Log.e("error:",e.getMessage());
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(),"Load error",Toast.LENGTH_SHORT).show();
//            }
//        });
        return newListSanPham;
    }

    private ArrayList<SanPham> getListQuan(ArrayList<SanPham> listSP){
        newListSanPham=new ArrayList<>();
        for (SanPham sp: listSP
             ) {
            if(sp.getLoaisp().equals("Quần")){
                newListSanPham.add(0,sp);
            }
        }
        sanPhamAdapter.notifyDataSetChanged();
//        FirebaseDatabase database=FirebaseDatabase.getInstance();
//        DatabaseReference myRef= database.getReference("SanPham");
//        mlistSanPham=new ArrayList<>();
//        Query query=myRef.orderByChild("loaisp").equalTo("Quần");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    try {
//                        SanPham sanPham= dataSnapshot.getValue(SanPham.class);
//                        mlistSanPham.add(0,sanPham);
//                        sanPhamAdapter.notifyDataSetChanged();
//                    } catch (Exception e){
//                        Log.e("error:",e.getMessage());
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(),"Load error",Toast.LENGTH_SHORT).show();
//            }
//        });
//        Log.e("listSP",mlistSanPham.toString());
        return newListSanPham;
    }

    private void init(){
        mViewpager=homeView.findViewById(R.id.viewpager);
        mCircleIndicator=homeView.findViewById(R.id.indicator);
        btnSearch=homeView.findViewById(R.id.btnSearch);
        mRecycleView=homeView.findViewById(R.id.recycleView);
        rdAo=homeView.findViewById(R.id.selectAo);
        rdQuan=homeView.findViewById(R.id.selectQuan);
        rdTatca=homeView.findViewById(R.id.selectAll);
    }
    private List<Photo> getListPhoto(){
        List<Photo> listPhoto=new ArrayList<>();
        listPhoto.add(new Photo(R.drawable.aosomi));
        listPhoto.add(new Photo(R.drawable.aothun1));
        listPhoto.add(new Photo(R.drawable.vest1));
        listPhoto.add(new Photo(R.drawable.quanjean1));
        listPhoto.add(new Photo(R.drawable.quantay1));
        return listPhoto;
    }
//


    @Override
    public void onPause() {
        super.onPause();
        photoHandler.removeCallbacks(photoRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        photoHandler.postDelayed(photoRunnable,2000);
    }
}