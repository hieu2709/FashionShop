package com.example.fashionshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fashionshop.R;
import com.example.fashionshop.fragment.HomeFragment;
import com.example.fashionshop.object.SanPham;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM=1;
    private static final int TYPE_LOADING=2;
    private HomeFragment mContext;
    private boolean isLoadingAdd;
    private List<SanPham> listSanPham;
    private IClickItemSP iClickItemSP;

    @Override
    public int getItemViewType(int position) {
        if(listSanPham!=null && position==listSanPham.size()-1 && isLoadingAdd){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    public SanPhamAdapter(HomeFragment mContext, IClickItemSP listener) {
        this.mContext = mContext;
        this.iClickItemSP=listener;
    }
    public void setData(List<SanPham> list){
        this.listSanPham=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(TYPE_ITEM==viewType){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham,parent,false);
            return new SanPhamViewHolder(view);
        }else {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()==TYPE_ITEM){
            SanPham sanPham=listSanPham.get(position);
            SanPhamViewHolder sanPhamViewHolder=(SanPhamViewHolder) holder;

        if(sanPham==null){
            return;
        }
            sanPhamViewHolder.tvTensp.setText(sanPham.getTensp());
            sanPhamViewHolder.tvGiasp.setText(String.valueOf(sanPham.getGia()));
        Glide.with(mContext).load(sanPham.getHinhanh()).into(sanPhamViewHolder.imgSanPham);
            sanPhamViewHolder.item_layout.setOnClickListener(view -> {
            iClickItemSP. onClickSP(view,sanPham);
        });
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        SanPham sanPham=listSanPham.get(position);
//        if(sanPham==null){
//            return;
//        }
//        holder.tvTensp.setText(sanPham.getTensp());
//        holder.tvGiasp.setText(String.valueOf(sanPham.getGia()));
//        Glide.with(mContext).load(sanPham.getHinhanh()).into(holder.imgSanPham);
//        holder.item_layout.setOnClickListener(view -> {
//            iClickItemSP. onClickSP(view,sanPham);
//        });
////        holder.imgSanPham.setImageResource(R.drawable.aosomi);
//    }

    @Override
    public int getItemCount() {
        if(listSanPham!=null){
            return listSanPham.size();
        }
        return 0;
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout item_layout;
        private ImageView imgSanPham;
        private TextView tvTensp;
        private TextView tvGiasp;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            item_layout=itemView.findViewById(R.id.layout_item);
            imgSanPham=itemView.findViewById(R.id.img_sanpham);
            tvTensp=itemView.findViewById(R.id.tv_tensp);
            tvGiasp=itemView.findViewById(R.id.tv_giasp);
        }
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progress_bar);
        }
    }

    public interface IClickItemSP{
       public void onClickSP(View view, SanPham sp);
    }
    public void addFooterLoading(){
        isLoadingAdd=true;
    }
    public void removeFooterLoading(){
        isLoadingAdd=false;
        int position = listSanPham.size()-1;
        SanPham sanPham=listSanPham.get(position);
        if(sanPham!=null){
            listSanPham.remove(position);
            notifyItemRemoved(position);
        }
    }
}
