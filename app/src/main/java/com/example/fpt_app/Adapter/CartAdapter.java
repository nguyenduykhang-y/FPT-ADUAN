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

import com.example.fpt_app.Models.Cart;

import com.example.fpt_app.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> data;
    private Context context;
    private RecyclerView mRecyclerView;

    public CartAdapter(Context context, List<Cart> data) {
        this.data = data;
        this.context = context;
    }

    public CartAdapter(ArrayAdapter<String> arrayAdapter) {
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);

        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        final Cart cart = data.get(position);
        if (cart == null){
            return;
        }
        Glide.with(context).load(cart.getImage_url())
                .into(holder.proImg);
        holder.tvName.setText(cart.getName());
        holder.tvPrice.setText(String.valueOf(cart.getPrice())+"VNĐ");
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
        if(data !=null ){
            return data.size();
        }

        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        private ImageView proImg;
        private TextView tvName, tvPrice;
        private CardView mCardView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            proImg = itemView.findViewById(R.id.imgCart);
            tvName = itemView.findViewById(R.id.tvNameCart);
            tvPrice = itemView.findViewById(R.id.tvPriceCart);
            mCardView = itemView.findViewById(R.id.layout_item);
        }
    }
}
