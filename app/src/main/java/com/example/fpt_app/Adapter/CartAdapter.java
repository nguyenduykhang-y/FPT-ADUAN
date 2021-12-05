package com.example.fpt_app.Adapter;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AlertDialog;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>  {
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private List<Cart> data;
    private Context context;
//    private RecyclerView mRecyclerView;
    private static int count = 0;
    private double soluong= 1;

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
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getRootView().getContext());
               alertDialog.setTitle("Bỏ like Sản phẩm");
               alertDialog.setIcon(R.drawable.delete_bin_48px);
               alertDialog.setMessage("Bạn có muốn bỏ sản phẩm này không ?" + cart.getName());
               alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });
               alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

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
                       data.remove(position);
                       notifyDataSetChanged();

                   }
               });
               alertDialog.show();
           }
       });
//       holder.btnCong.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//                soluong = soluong + 1;
//                holder.quantity.setText(String.valueOf(soluong));
//
//           }
//       });
//        holder.btnTru.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                soluong = soluong - 1;
//                holder.quantity.setText(String.valueOf(soluong));
//            }
//        });


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
        private TextView tvName, tvPrice;
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

        }
    }

}
