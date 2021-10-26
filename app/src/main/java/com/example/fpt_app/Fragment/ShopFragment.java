package com.example.fpt_app.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fpt_app.Adapter.ListAdapter;
import com.example.fpt_app.Adapter.ShopAdapter;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.R;

import java.util.ArrayList;
import java.util.List;


public class ShopFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        mRecyclerView= view.findViewById(R.id.Rcv_ListViewProducts);
        gridLayoutManager = new GridLayoutManager(getContext(), 2 );
        mRecyclerView.setLayoutManager(gridLayoutManager);

        ShopAdapter adaptershop = new ShopAdapter(getContext(),getlist());
        mRecyclerView.setAdapter(adaptershop);


       return  view;
    }


    private List<Shop> getlist() {
        List<Shop> shop = new ArrayList<>();
        shop.add(new Shop(R.drawable.shop1,"name","45 lê quang định"));
        shop.add(new Shop(R.drawable.shop2,"name","45 lê quang định"));
        shop.add(new Shop(R.drawable.shop3,"name","45 lê quang định"));
        shop.add(new Shop(R.drawable.shop1,"name","45 lê quang định"));

        return shop;
    }
}