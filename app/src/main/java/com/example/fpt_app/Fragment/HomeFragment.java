package com.example.fpt_app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fpt_app.Adapter.ListAdapter;
import com.example.fpt_app.Adapter.PhotoViewPager;
import com.example.fpt_app.Adapter.Silder;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Silder> mSilders;
    List<ListSP> mlisSP;
    private GridLayoutManager gridLayoutManager;
    RecyclerView mRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }
    private Handler mhHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager.getCurrentItem() == mSilders.size() - 1){
                mViewPager.setCurrentItem(0);
            }else {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        //ánh xạ
        mViewPager = view.findViewById(R.id.view_images);
        mCircleIndicator  = view.findViewById(R.id.circle_indicatior);
        mSilders = getListPhoto();
        PhotoViewPager adapter = new PhotoViewPager(mSilders);
        mViewPager.setAdapter(adapter);
        mCircleIndicator.setViewPager(mViewPager);
        mRecyclerView = view.findViewById(R.id.Rcv_ListViewProducts);

        //custom rcv
        gridLayoutManager = new GridLayoutManager(getContext(), 2 );
        mRecyclerView.setLayoutManager(gridLayoutManager);

        //set adapterSP
        ListAdapter adapter1 = new ListAdapter(getContext(),getlist());
        mRecyclerView.setAdapter(adapter1);

//        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        //Banner list
        mhHandler.postDelayed(mRunnable, 2100);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mhHandler.removeCallbacks(mRunnable);
                mhHandler.postDelayed(mRunnable, 2100);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return view;
    }
    //getListSP
    private List<ListSP> getlist() {
        List<ListSP> list = new ArrayList<>();
        list.add(new ListSP(R.drawable.laptop, "Asus Zenbook Q407IQ Ryzen 5 4500U/ RAM 8GB/ SSD 256GB/ MX350/ 14 INCH FHD (Brand...","17,490,000đ", ListSP.TYPE_HP));
        list.add(new ListSP(R.drawable.laptop, "Asus Zenbook Q407IQ Ryzen 5 4500U/ RAM 8GB/ SSD 256GB/ MX350/ 14 INCH FHD (Brand...","17,490,000đ", ListSP.TYPE_HP));
        list.add(new ListSP(R.drawable.laptop, "Asus Zenbook Q407IQ Ryzen 5 4500U/ RAM 8GB/ SSD 256GB/ MX350/ 14 INCH FHD (Brand...","17,490,000đ", ListSP.TYPE_HP));
        list.add(new ListSP(R.drawable.laptop, "Asus Zenbook Q407IQ Ryzen 5 4500U/ RAM 8GB/ SSD 256GB/ MX350/ 14 INCH FHD (Brand...","17,490,000đ", ListSP.TYPE_HP));
        list.add(new ListSP(R.drawable.laptop, "Asus Zenbook Q407IQ Ryzen 5 4500U/ RAM 8GB/ SSD 256GB/ MX350/ 14 INCH FHD (Brand...","17,490,000đ", ListSP.TYPE_HP));
        list.add(new ListSP(R.drawable.laptop, "Asus Zenbook Q407IQ Ryzen 5 4500U/ RAM 8GB/ SSD 256GB/ MX350/ 14 INCH FHD (Brand...","17,490,000đ", ListSP.TYPE_HP));

        list.add(new ListSP(R.drawable.gamning, "Razer Blade 15 (2018) Core i7 8750H / RAM 16GB / SSD 512GB / GTX 1070 / 144Hz (No.2827)",
                "31.499.000 đ", ListSP.TYPE_GM));
        list.add(new ListSP(R.drawable.gamning, "Razer Blade 15 (2018) Core i7 8750H / RAM 16GB / SSD 512GB / GTX 1070 / 144Hz (No.2827)",
                "31.499.000 đ", ListSP.TYPE_GM));
        list.add(new ListSP(R.drawable.gamning, "Razer Blade 15 (2018) Core i7 8750H / RAM 16GB / SSD 512GB / GTX 1070 / 144Hz (No.2827)",
                "31.499.000 đ", ListSP.TYPE_GM));
        list.add(new ListSP(R.drawable.gamning, "Razer Blade 15 (2018) Core i7 8750H / RAM 16GB / SSD 512GB / GTX 1070 / 144Hz (No.2827)",
                "31.499.000 đ", ListSP.TYPE_GM));
        list.add(new ListSP(R.drawable.gamning, "Razer Blade 15 (2018) Core i7 8750H / RAM 16GB / SSD 512GB / GTX 1070 / 144Hz (No.2827)",
                "31.499.000 đ", ListSP.TYPE_GM));

        list.add(new ListSP(R.drawable.laptopas, "Laptop HP 240 G8 i5 1135G7/8GB/512GB/14.0HD/Win 10",
                "31.499.000 đ", ListSP.TYPE_ASUS));
        list.add(new ListSP(R.drawable.laptopas, "Laptop HP 240 G8 i5 1135G7/8GB/512GB/14.0HD/Win 10",
                "31.499.000 đ", ListSP.TYPE_ASUS));
        list.add(new ListSP(R.drawable.laptopas, "Laptop HP 240 G8 i5 1135G7/8GB/512GB/14.0HD/Win 10",
                "31.499.000 đ", ListSP.TYPE_ASUS));
        list.add(new ListSP(R.drawable.laptopas, "Laptop HP 240 G8 i5 1135G7/8GB/512GB/14.0HD/Win 10",
                "31.499.000 đ", ListSP.TYPE_ASUS));
        list.add(new ListSP(R.drawable.laptopas, "Laptop HP 240 G8 i5 1135G7/8GB/512GB/14.0HD/Win 10",
                "31.499.000 đ", ListSP.TYPE_ASUS));

        return list;
    }


    //list image banner
    private List<Silder> getListPhoto() {
        List<Silder> list = new ArrayList<>();
        list.add(new Silder(R.drawable.backgourd));
        list.add(new Silder(R.drawable.backgroud1));
        list.add(new Silder(R.drawable.backgourd2));
        return list;
    }
}