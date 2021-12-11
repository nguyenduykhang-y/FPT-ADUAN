package com.example.fpt_app.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.fpt_app.CartActivity;
import com.example.fpt_app.DetailsActivity;
import com.example.fpt_app.Models.Cart;

import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.ProductActivity;
import com.example.fpt_app.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>  {
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private List<Cart> data;
    private Map<Cart, Integer> cartMap;
    private Context context;
//    private RecyclerView mRecyclerView;
    private static int count = 0;
    private int  soluong= 1;
    private TextView txtGiaTien;

    public List<Cart> getData() {
        return data;
    }

    public CartAdapter(Context context, List<Cart> data) {
        this.data = data;
        cartMap = new HashMap<>();
        data.forEach(cart -> {
            //TODO: set real q
            cartMap.put(cart, 1);
        });
        this.context = context;
        txtGiaTien = ((Activity) context).findViewById(R.id.TvGiaTien);

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
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Cart cart = data.get(position);
        if (cart == null){
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
        Glide.with(context).load(cart.getImage_url())
                .into(holder.proImg);
        holder.tvName.setText(cart.getName());
        holder.tvPrice.setText(decimalFormat.format(cart.getPrice())+" VND");
       holder.imgdelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //pass the 'context' here
               android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(v.getRootView().getContext());

               View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.custom_dialog, null);
               ImageView img ;
               Button btnNo, btnYes;
               TextView tvTC, tvText;
               img = dialogView.findViewById(R.id.imgDialog);
               btnNo = dialogView.findViewById(R.id.btnNo);
               btnYes = dialogView.findViewById(R.id.btnYes);
               tvTC = dialogView.findViewById(R.id.tvTC);
               tvText = dialogView.findViewById(R.id.tvText);
               alertDialog.setView(dialogView);
               alertDialog.setCancelable(true);
               AlertDialog dialog = alertDialog.create();
               dialog.show();

               btnNo.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       dialog.cancel();
                   }
               });
               btnYes.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       // DO SOMETHING HERE
                       IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);

                       service.cart_delete(data.get(position)).enqueue(new Callback<ResponseModel>() {
                           @Override
                           public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                               if (response.isSuccessful()){
                                   ResponseModel model = response.body();
                                   if(model.getStatus()){
                                       IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                                       service.LikeGetALL();
                                   } else {
                                       Log.e(">>>>>deleteCB getStatus failed", "detele failed");
                                   }
                               } else{
                                   Log.e(">>>>>deleteCB onResponse", response.message());
                               }
                           }

                           @Override
                           public void onFailure(Call<ResponseModel> call, Throwable t) {
                               Log.d("fail",""+t.getMessage() );
                           }
                       });
                       cartMap.remove(data.get(position));
                       data.remove(position);
                       txtGiaTien.setText(getTotal());
                       notifyDataSetChanged();
                       dialog.cancel();
                   }
               });
           }
       });
       holder.btnCong.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                soluong = soluong + 1;
                Cart c = data.get(position);
                cartMap.put(c, soluong);
                holder.quantity.setText(String.valueOf(soluong));

               txtGiaTien.setText(getTotal());
           }
       });

        holder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluong = soluong - 1;
                holder.quantity.setText(String.valueOf(soluong));
            }
        });


    }

    private String getTotal(){
        double sum = 0;
        for (Map.Entry<Cart, Integer> e : cartMap.entrySet()){
            sum += e.getKey().getPrice() * e.getValue();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
        return decimalFormat.format(sum)+" VNĐ";
    }



    @Override
    public int getItemCount() {
        if(data !=null ){
            return data.size();
        }

        return 0;
    }



    public class CartViewHolder extends RecyclerView.ViewHolder{
        private ImageView proImg,imgdelete;
        private TextView tvName, tvPrice,tvSumPrice;
        private EditText quantity;
        private CardView mCardView;
        private Button btnTru,btnCong;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            proImg = itemView.findViewById(R.id.imgCart);
            tvName = itemView.findViewById(R.id.tvNameCart);
            tvPrice = itemView.findViewById(R.id.tvPriceCart);
            mCardView = itemView.findViewById(R.id.cart_item);
            imgdelete = itemView.findViewById(R.id.imgdelete);
            quantity = itemView.findViewById(R.id.tvQuantity);
            btnTru= itemView.findViewById(R.id.btnTru);
            btnCong = itemView.findViewById(R.id.btnCong);
            tvSumPrice= itemView.findViewById(R.id.TvGiaTien);

        }
    }

}
