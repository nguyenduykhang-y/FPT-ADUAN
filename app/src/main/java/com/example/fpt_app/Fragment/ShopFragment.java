package com.example.fpt_app.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
<<<<<<< HEAD
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
=======
>>>>>>> 8a58581 (xửa thành fragment)

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< HEAD
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
=======
import com.example.fpt_app.R;


public class ShopFragment extends Fragment {


>>>>>>> 8a58581 (xửa thành fragment)
    public ShopFragment() {
        // Required empty public constructor
    }


<<<<<<< HEAD
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
=======



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
>>>>>>> 8a58581 (xửa thành fragment)
    }
}