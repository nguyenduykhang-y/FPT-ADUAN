package com.example.fpt_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class UserInsertAdapter extends BaseAdapter {
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private List<Product> data;
    private Context context;


    public UserInsertAdapter(List<Product> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
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

    public Object getItem(int i) {
        return data.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View View = view;
        if (view == null){
            view = View.inflate(viewGroup.getContext(), R.layout.item_user_insert, null);
            TextView tvUserProductName = (TextView) view.findViewById(R.id.tvUserProductName);
            TextView tvUserProductPrice = (TextView) view.findViewById(R.id.tvUserProductPrice);


            ImageView userProImg = (ImageView) view.findViewById(R.id.ivUserProductImage);
            UserInsertViewHolder holder = new UserInsertViewHolder(tvUserProductName, tvUserProductPrice, userProImg);
            view.setTag(holder);
        }
        UserInsertViewHolder holder = (UserInsertViewHolder) view.getTag();
        Product p = (Product) getItem(i);
        holder.tvUserProductName.setText(p.getName());
        holder.tvUserProductPrice.setText(String.valueOf(p.getPrice()) + " VNĐ");
        Glide.with(context).load(p.getImage_url())
                .into(holder.userProImg);

        return view;
    }

    private static class UserInsertViewHolder{
        final TextView tvUserProductName, tvUserProductPrice;
        final ImageView userProImg;

        public UserInsertViewHolder(TextView tvUserProductName, TextView tvUserProductPrice, ImageView userProImg) {
            this.tvUserProductName = tvUserProductName;
            this.tvUserProductPrice = tvUserProductPrice;
            this.userProImg = userProImg;
        }
    }
}
