package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fpt_app.Models.ListSP;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null ){
            return;
        }
        ListSP listSP = (ListSP) bundle.get("chitiet");
        ImageView img = findViewById(R.id.imgdetail);
        TextView tv = findViewById(R.id.tvTesst);
        Button btn = findViewById(R.id.mua);
        TextView tvGia = findViewById(R.id.tvGia);
        img.setImageResource(listSP.getImg());
        tv.setText(listSP.getName());
        tvGia.setText(listSP.getGia());
        btn.setText(listSP.getGia());
    }
}