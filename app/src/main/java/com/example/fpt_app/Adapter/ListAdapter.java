package com.example.fpt_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpt_app.DetailsActivity;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<ListSP> mListSPS;
    private Context mContext;

    public ListAdapter( Context context, List<ListSP> mListSPS) {
        this.mListSPS = mListSPS;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ListSP listSP = mListSPS.get(position);
        if (listSP==null){
            return;
        }
        holder.img.setImageResource(listSP.getImg());
        holder.tvName.setText(listSP.getName());
        holder.tvGia.setText(listSP.getGia());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListGoOn(listSP);
            }
        });
    }

    private void onListGoOn(ListSP listSP) {
        Intent i = new Intent(mContext, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ọject", listSP);
        i.putExtras(bundle);
        mContext.startActivity(i);
    }

    @Override
    public int getItemCount() {
        if(mListSPS !=null ){
            return mListSPS.size();
        }

        return 0;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tvName, tvGia;
        private CardView mCardView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_list);
            tvName = itemView.findViewById(R.id.nameSP);
            tvGia = itemView.findViewById(R.id.giaSP);
            mCardView = itemView.findViewById(R.id.layout_item);
        }
    }
}
