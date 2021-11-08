package com.example.fpt_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fpt_app.Models.Erea;
import com.example.fpt_app.R;

import java.util.List;

public class EreaAdapter extends ArrayAdapter<Erea> {

    public EreaAdapter(@NonNull Context context, int resource, @NonNull List<Erea> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent ,false);
        TextView tvSelected = convertView.findViewById(R.id.selected);
        Erea erea = this.getItem(position);
        if (erea != null ){
            tvSelected.setText(erea.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_erea, parent ,false);
        TextView tv = convertView.findViewById(R.id.tv_erea);

        Erea erea = this.getItem(position);
        if (erea != null ){
            tv.setText(erea.getName());
        }
        return convertView;
    }
}
