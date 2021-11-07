package com.example.fpt_app.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpt_app.Adapter.CartAdapter;
import com.example.fpt_app.Adapter.ShopAdapter;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    public CartFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        mRecyclerView= view.findViewById(R.id.lvCart);
        gridLayoutManager = new GridLayoutManager(getContext(), 2 );
        mRecyclerView.setLayoutManager(gridLayoutManager);

        CartAdapter adaptercart = new CartAdapter(getlist(), getContext());
        mRecyclerView.setAdapter(adaptercart);


        return  view;
    }
    private List<Cart> getlist() {
        List<Cart> cart = new ArrayList<>();
        cart.add(new Cart(R.drawable.shop1, "Asus", 150000000));
        cart.add(new Cart(R.drawable.shop1, "Asus", 150000000));
        cart.add(new Cart(R.drawable.shop1, "Asus", 150000000));
        cart.add(new Cart(R.drawable.shop1, "Asus", 150000000));

        return cart;
    }
}
