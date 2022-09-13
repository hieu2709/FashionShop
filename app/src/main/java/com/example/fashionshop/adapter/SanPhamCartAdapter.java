package com.example.fashionshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fashionshop.R;
import com.example.fashionshop.fragment.ShoppingCartFragment;
import com.example.fashionshop.object.SanPhamCart;

import java.util.List;

public class SanPhamCartAdapter extends RecyclerView.Adapter<SanPhamCartAdapter.SanPhamCartViewHolder> {
    private ShoppingCartFragment mContext;
    private List<SanPhamCart> listSpCart;
    private IClickItemSPCart iClickItemSP;

    public SanPhamCartAdapter(ShoppingCartFragment mContext,IClickItemSPCart listener) {
        this.mContext = mContext;
        this.iClickItemSP=listener;
    }
    public void setData(List<SanPhamCart> list){
        this.listSpCart=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SanPhamCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham_cart,parent,false);
        return new SanPhamCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamCartAdapter.SanPhamCartViewHolder holder, int position) {
        SanPhamCart sanPham=listSpCart.get(position);
        if(sanPham==null){
            return;
        }
        holder.tvTensp.setText(sanPham.getTensp());
        holder.tvGiasp.setText(String.valueOf(sanPham.getGia()));
        Glide.with(mContext).load(sanPham.getHinhanh()).into(holder.imgSanPham);
        holder.soluong.setText(String.valueOf(sanPham.getSoluong()));
        holder.daucong.setOnClickListener(view -> {
            int so_luong_cu=Integer.parseInt(holder.soluong.getText().toString());
            holder.soluong.setText(String.valueOf(so_luong_cu+1));
            iClickItemSP.onClickCong(sanPham,Integer.parseInt(holder.soluong.getText().toString()));
        });
        holder.dautru.setOnClickListener(view -> {
            int so_luong_cu=Integer.parseInt(holder.soluong.getText().toString());
            holder.soluong.setText(String.valueOf(so_luong_cu-1));
            if(Integer.parseInt(holder.soluong.getText().toString())<1){
                holder.soluong.setText("1");
            }
            iClickItemSP.onClickTru(sanPham,Integer.parseInt(holder.soluong.getText().toString()));
        });
        holder.item_layout.setOnClickListener(view -> {
            iClickItemSP.onClickSP(view,sanPham);
        });
        holder.btnHuy.setOnClickListener(view -> {
            iClickItemSP.onClickHuy(view,sanPham);
        });
    }

    @Override
    public int getItemCount() {
        if(listSpCart!=null){
            return listSpCart.size();
        }
        return 0;
    }

    public class SanPhamCartViewHolder extends RecyclerView.ViewHolder{
        public ConstraintLayout item_layout;
        private ImageView imgSanPham;
        private TextView tvTensp;
        private TextView tvGiasp;
        private TextView daucong,dautru,soluong,btnHuy;
        public SanPhamCartViewHolder(@NonNull View itemView) {
            super(itemView);
            item_layout=itemView.findViewById(R.id.item_cart);
            imgSanPham=itemView.findViewById(R.id.img_sanphamcart);
            tvTensp=itemView.findViewById(R.id.tv_tenspcart);
            tvGiasp=itemView.findViewById(R.id.tv_giaspcart);
            daucong=itemView.findViewById(R.id.dau_congcart);
            dautru=itemView.findViewById(R.id.dau_trucart);
            soluong=itemView.findViewById(R.id.ed_soluongcart);
            btnHuy=itemView.findViewById(R.id.btnHuy);
        }
    }

    public interface IClickItemSPCart{
        public void onClickSP(View view, SanPhamCart spcart);
        public void onClickHuy(View view, SanPhamCart spcart);
        public void onClickCong(SanPhamCart spCart,int soluong);
        public void onClickTru(SanPhamCart spCart, int soluong);
    }
}
