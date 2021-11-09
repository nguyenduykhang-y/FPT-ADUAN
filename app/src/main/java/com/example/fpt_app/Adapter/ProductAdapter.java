package com.example.fpt_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.fpt_app.DetailsActivity;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.ProductFormActivity;
import com.example.fpt_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> data;
    private Context context;
    private List<Product> origiaItems;
    public ProductAdapter(List<Product> data, Context context) {
        this.data = data;
        this.context = context;
        this.origiaItems = new ArrayList<>();
        origiaItems.addAll(data);
    }

    public ProductAdapter(Context arrayAdapter) {
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item, parent, false);

        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = data.get(position);
        if (product == null){
            return;
        }
        Glide.with(context).load(product.getImage_url())
                .into(holder.image_url);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice())+"VNĐ");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, DetailsActivity.class);
                in.putExtra("id", product.getId());
                in.putExtra("imgesview",product.getImage_url());
                in.putExtra("name", product.getName());
                in.putExtra("quantily", String.valueOf(product.getQuantity()));
                in.putExtra("category_id",  String.valueOf(product.getCategory_id()));
                in.putExtra("price", String.valueOf(product.getPrice()));

                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
        });
    }



    @Override
    public int getItemCount() {
        if (data != null){
            return data.size();
        }
        return 0;
    }
    public void filter(String strSearch){
        if (strSearch.length() == 0){
            data.clear();
            data.addAll(origiaItems);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                data.clear();
                List<Product> collect = origiaItems.stream()
                        .filter(i -> i.getName().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                data.addAll(collect);
            }
            else {
                data.clear();
                for (Product i : origiaItems) {
                    if (i.getName().toLowerCase().contains(strSearch)) {
                        data.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView  name, price;
        private ImageView image_url;
        private CardView cardView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewProductName);
            price = itemView.findViewById(R.id.textViewProductPrice);
            image_url = itemView.findViewById(R.id.imageViewProduct);
            cardView = itemView.findViewById(R.id.layout_item);
        }
    }

}
