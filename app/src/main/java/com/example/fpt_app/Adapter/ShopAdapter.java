package com.example.fpt_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpt_app.DetailsActivity;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.R;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<Shop> mShop;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public ShopAdapter(Context context, List<Shop> mShop) {
        this.mShop = mShop;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);

        return new ShopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        final Shop shop = mShop.get(position);
        if (shop == null){
            return;
        }
        holder.img.setImageResource(shop.getStoreImage());
        holder.tvName.setText(shop.getStoteName());
        holder.tvAddress.setText(shop.getStoreAddress());
//
//        holder.mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onListGoOn(shop);
//            }
//        });
    }

//    private void onListGoOn(Shop shop) {
//        Intent i = new Intent(mContext, DetailsActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("ọject",shop);
//        i.putExtras(bundle);
//        mContext.startActivity(i);
//    }

    @Override
    public int getItemCount() {
        if(mShop !=null ){
            return mShop.size();
        }

        return 0;
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tvName, tvAddress;
        private CardView mCardView;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_shop);
            tvName = itemView.findViewById(R.id.storeName);
            tvAddress = itemView.findViewById(R.id.storeAddress);
            mCardView = itemView.findViewById(R.id.layout_item);
        }
    }
}
