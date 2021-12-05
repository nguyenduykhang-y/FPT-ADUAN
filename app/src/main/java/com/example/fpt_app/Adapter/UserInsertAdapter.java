package com.example.fpt_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.ProductFormActivity;
import com.example.fpt_app.R;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInsertAdapter extends RecyclerView.Adapter<UserInsertAdapter.UserInsertViewHolder> {
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private List<Product> data;
    private Context context;


    public UserInsertAdapter(List<Product> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public UserInsertAdapter.UserInsertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_insert, parent, false);
        return new UserInsertViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInsertAdapter.UserInsertViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = data.get(position);
        if (product == null) {
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
        Glide.with(context).load(product.getImage_url())
                .into(holder.userProImg);
        holder.tvUserProductName.setText(product.getName());
        holder.tvUserProductPrice.setText(decimalFormat.format(product.getPrice()) + " VND");

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(context, ProductFormActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);

            }
        });
        holder.userDelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);

                service.productDelete(data.get(position)).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("fail",""+t.getMessage() );
                    }
                });
                data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, data.size());
                notifyDataSetChanged();
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

    public class UserInsertViewHolder extends RecyclerView.ViewHolder {
        private ImageView userProImg, userDelImg, userBackIcon;
        private TextView tvUserProductName, tvUserProductPrice;
        private CardView mCardView;

        public UserInsertViewHolder(@NonNull View itemView) {
            super(itemView);
            userProImg = itemView.findViewById(R.id.ivUserProductImage);
            tvUserProductName = itemView.findViewById(R.id.tvUserProductName);
            tvUserProductPrice = itemView.findViewById(R.id.tvUserProductPrice);
            mCardView = itemView.findViewById(R.id.user_insert_item);
            userDelImg = itemView.findViewById(R.id.ivUserProductDelete);
        }
    }
}
