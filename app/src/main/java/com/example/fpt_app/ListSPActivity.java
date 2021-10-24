package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.fpt_app.Adapter.ListAdapter;
import com.example.fpt_app.Models.ListSP;

import java.util.ArrayList;
import java.util.List;

public class ListSPActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private Button btnA, btnB, btnC;
    private GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_spactivity);

        btnA = findViewById(R.id.btnAsus);
        btnB = findViewById(R.id.btnHP);
        btnC = findViewById(R.id.btnGM);

        mRecyclerView=findViewById(R.id.rcv_list);
        gridLayoutManager = new GridLayoutManager(this, 2 );
        mRecyclerView.setLayoutManager(gridLayoutManager);

        ListAdapter adapter = new ListAdapter(this,getlist());
        mRecyclerView.setAdapter(adapter);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
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