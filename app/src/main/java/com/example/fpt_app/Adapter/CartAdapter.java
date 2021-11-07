package com.example.fpt_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Cart> mCart;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public CartAdapter(List<Cart> mCart, Context mContext) {
        this.mCart = mCart;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);

        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        final Cart cart = mCart.get(position);
        if (cart == null){
            return;
        }

        holder.productImage.setImageResource(cart.getProductImage());
        holder.productName.setText(cart.getProductName());
        holder.productPrice.setText(cart.getProductPrice());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productPrice, quantity;
        private Button increase, decrease;
        private CardView cartCardView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.ivPro);
            productName = itemView.findViewById(R.id.tvNamePro);
            productPrice = itemView.findViewById(R.id.tvPricePro);
            cartCardView = itemView.findViewById(R.id.cart_item);
            increase = itemView.findViewById(R.id.btnIncrease);
            decrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}
