package com.example.fpt_app.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fpt_app.DetailsActivity;
import com.example.fpt_app.DetailsLikeActivity;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Like;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.R;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.CartViewHolder>  {
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private List<Like> data;
    private Context context;
//    private RecyclerView mRecyclerView;
    private static int count = 0;
    private int pr;

    public LikeAdapter(Context context, List<Like> data) {
        this.data = data;
        this.context = context;

    }

    public LikeAdapter(ArrayAdapter<String> arrayAdapter) {
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false);

        return new CartViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Like like = data.get(position);
        if (like == null){
            return;
        }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
            Glide.with(context).load(like.getImage_url())
                    .into(holder.proImg);
            holder.tvName.setText(like.getName());
            holder.tvPrice.setText(decimalFormat.format(like.getPrice())+" VND");



        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, DetailsLikeActivity.class);
                in.putExtra("id",String.valueOf(like.getId()));
                in.putExtra("imgesview",String.valueOf(like.getImage_url()));
                in.putExtra("name", like.getName());
                in.putExtra("quantity",String.valueOf(like.getQuantity()));
                in.putExtra("category_id",String.valueOf(like.getCategory_id()));
                in.putExtra("price", String.valueOf(like.getPrice()));
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);

            }
        });
        holder.dele.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //pass the 'context' here
               //pass the 'context' here
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getRootView().getContext());
               alertDialog.setTitle("Bỏ like Sản phẩm");
               alertDialog.setIcon(R.drawable.delete_bin_48px);
               alertDialog.setMessage("Bạn có muốn bỏ like sản phẩm này không ?" + like.getName());
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

                       service.likeDelete(data.get(position)).enqueue(new Callback<ResponseModel>() {
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

    }


    @Override
    public int getItemCount() {
        if(data !=null ){
            return data.size();
        }

        return 0;
    }




    public class CartViewHolder extends RecyclerView.ViewHolder{
        private ImageView proImg;
        private TextView tvName, tvPrice, quantity;
        private CardView mCardView;
        private Button dele,btnNo;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            proImg = itemView.findViewById(R.id.imgCart);
            tvName = itemView.findViewById(R.id.tvNameCart);
            tvPrice = itemView.findViewById(R.id.tvPriceCart);
            mCardView = itemView.findViewById(R.id.cart_item);
            dele = itemView.findViewById(R.id.imgdelete);

        }
    }

}
