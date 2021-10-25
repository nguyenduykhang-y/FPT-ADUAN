package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        ListSP listSP = (ListSP) bundle.get("ọject");
        TextView tv = findViewById(R.id.tvTesst);
        TextView tvGia = findViewById(R.id.tvGia);
        tv.setText(listSP.getName());
        tvGia.setText(listSP.getGia());
    }
}