package com.example.fashionshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionshop.R;
import com.example.fashionshop.object.Photo;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyHolder> {
    private  List<Photo> mlistPhoto;

    public SliderAdapter(List<Photo> mlistPhoto) {
        this.mlistPhoto = mlistPhoto;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_slide,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Photo photo=mlistPhoto.get(position);
        holder.imgPhoto.setImageResource(photo.getRsID());
    }

    @Override
    public int getItemCount() {
        if(mlistPhoto!=null){
            return mlistPhoto.size();
        }
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto=itemView.findViewById(R.id.imgPhoto);
        }
    }
}
