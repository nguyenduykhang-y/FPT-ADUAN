package com.example.fpt_app.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fpt_app.Fragment.HomeFragment;
import com.example.fpt_app.Fragment.NotiFragment;
import com.example.fpt_app.Fragment.ProductFragment;
import com.example.fpt_app.Fragment.ShopFragment;
import com.example.fpt_app.Fragment.UserFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeFragment f1 = new HomeFragment();
                return f1;
            case 1:
                ProductFragment f2 = new ProductFragment ();
                return f2;
            case 2:
                ShopFragment f3 = new ShopFragment();
                return f3;
            case 3:
                NotiFragment f4 = new NotiFragment();
                return f4;
            case 4:
                UserFragment f5 = new UserFragment();
                return  f5;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
