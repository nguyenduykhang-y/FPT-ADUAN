package com.example.fpt_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.OderCT;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.R;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OderCTAdapter extends RecyclerView.Adapter<OderCTAdapter.CartViewHolder>  {
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private List<OderCT> data;
    private Context context;
//    private RecyclerView mRecyclerView;
    private static int count = 0;
    private double soluong= 1;

    public OderCTAdapter(Context context, List<OderCT> data) {
        this.data = data;
        this.context = context;

    }

    public OderCTAdapter(ArrayAdapter<String> arrayAdapter) {
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oderct, parent, false);

        return new CartViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final OderCT oderCT = data.get(position);
        Product product = new Product();

        if (oderCT == null){
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
//            if (id == product.getId()){
//                holder.tvnameSP.setText(product.getName());
//            }
        holder.tvnameSP.setText("M?? ????n h??ng: " +oderCT.getOderctId());
        holder.tvPrice.setText("Gi??: " + decimalFormat.format(oderCT.getPrice())+" VND");
        holder.tvQuantity.setText("S??? l?????ng: " + oderCT.getQuantity());
        holder.tvDate.setText("Ng??y: " +oderCT.getDate());




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
        private TextView tvnameSP, tvPrice,tvQuantity,tvDate;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnameSP = itemView.findViewById(R.id.tvnameSP);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity= itemView.findViewById(R.id.tvQuantity);
            tvDate= itemView.findViewById(R.id.tvDate);

        }
    }

}
