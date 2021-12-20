package com.example.fpt_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fpt_app.ChiTietOfShopActivity;
import com.example.fpt_app.DetailsActivity;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.R;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<Shop> mShop;
    private Context context;
    private RecyclerView mRecyclerView;

    public ShopAdapter(Context context, List<Shop> mShop) {
        this.mShop = mShop;
        this.context = context;
    }

    public ShopAdapter(ArrayAdapter<String> arrayAdapter) {
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);

        return new ShopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Shop shop = mShop.get(position);
        if (shop == null){
            return;
        }
        Glide.with(context).load(shop.getStoreImage())
                .into(holder.storeImg);
        holder.tvName.setText(shop.getStoreName());
        holder.tvAddress.setText("Địa Chỉ: "+ shop.getStoreAddress());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ChiTietOfShopActivity.class);
                in.putExtra("imgShop",String.valueOf(shop.getStoreImage()));
                in.putExtra("name", shop.getStoreName());
                in.putExtra("address", shop.getStoreAddress());
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mShop !=null ){
            return mShop.size();
        }

        return 0;
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder{
        private ImageView storeImg;
        private TextView tvName, tvAddress;
        private CardView mCardView;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            storeImg = itemView.findViewById(R.id.img_shop);
            tvName = itemView.findViewById(R.id.storeName);
            tvAddress = itemView.findViewById(R.id.storeAddress);
            mCardView = itemView.findViewById(R.id.layout_item);
        }
    }
}
