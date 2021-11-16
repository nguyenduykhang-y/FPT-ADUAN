package com.example.fpt_app.Adapter;

import android.content.Context;
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
import com.example.fpt_app.Models.TB;
import com.example.fpt_app.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongbaoAdapter extends RecyclerView.Adapter<ThongbaoAdapter.TBViewHolder> {

    private List<TB> data;
    private Context context;
    private RecyclerView mRecyclerView;
    ArrayList<Double> listong = new ArrayList<>();
    private static int count = 0;
    private int pr;

    public ThongbaoAdapter(Context context, List<TB> data) {
        this.data = data;
        this.context = context;
    }

    public ThongbaoAdapter(ArrayAdapter<String> arrayAdapter) {
    }

    @NonNull
    @Override
    public TBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tb_custom_item, parent, false);

        return new TBViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull TBViewHolder holder, int position) {
        final TB tb = data.get(position);
        if (tb == null){
            return;
        }
        holder.tvTitel.setText(tb.getNameTB());
        holder.tvND.setText(tb.getNoiDung());

    }

    public  void tong(){

    }

    @Override
    public int getItemCount() {
        if(data !=null ){
            return data.size();
        }

        return 0;
    }

    public class TBViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitel, tvND;
        private CardView mCardView;

        public TBViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitel = itemView.findViewById(R.id.txtTitle);
            tvND = itemView.findViewById(R.id.txtND);

        }
    }

}
