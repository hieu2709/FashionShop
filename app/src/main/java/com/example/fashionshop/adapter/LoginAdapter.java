package com.example.fashionshop.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fashionshop.fragment.SignIn_tab;
import com.example.fashionshop.fragment.SignUp_tab;

public class LoginAdapter extends FragmentStateAdapter {


    public LoginAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new SignIn_tab();
            case 1: return new SignUp_tab();
            default:return new SignIn_tab();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
