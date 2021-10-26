package com.example.fpt_app.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fpt_app.Adapter.ListAdapter;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.R;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private Button btnA, btnB, btnC;

    public ProductFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_product, container, false);

       //ánh xạ
        btnA = view.findViewById(R.id.btnAsus);
        btnB = view.findViewById(R.id.btnHP);
        btnC = view.findViewById(R.id.btnGM);

        mRecyclerView= view.findViewById(R.id.rcv_list);
        gridLayoutManager = new GridLayoutManager(getContext(), 2 );
        mRecyclerView.setLayoutManager(gridLayoutManager);

        ListAdapter adapter = new ListAdapter(getContext(),getlist());
        mRecyclerView.setAdapter(adapter);
        return view;
    }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAsus:
                scrollToItem(0);
            case R.id.btnHP:
                scrollToItem( 5);
            case R.id.btnGM:
                scrollToItem(10);
        }
    }

    private void scrollToItem(int i) {
        if (gridLayoutManager == null){
            return;
        }
        gridLayoutManager.scrollToPositionWithOffset(i, 0);
    }
}