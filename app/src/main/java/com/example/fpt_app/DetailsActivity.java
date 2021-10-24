package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
<<<<<<< HEAD
import android.widget.Button;
import android.widget.ImageView;
=======
>>>>>>> 9b3a8e1 (update)
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
<<<<<<< HEAD
        ListSP listSP = (ListSP) bundle.get("chitiet");
        ImageView img = findViewById(R.id.imgdetail);
        TextView tv = findViewById(R.id.tvTesst);
        Button btn = findViewById(R.id.mua);
        TextView tvGia = findViewById(R.id.tvGia);
        img.setImageResource(listSP.getImg());
        tv.setText(listSP.getName());
        tvGia.setText(listSP.getGia());
        btn.setText(listSP.getGia());
=======
        ListSP listSP = (ListSP) bundle.get("ọject");
        TextView tv = findViewById(R.id.tvTesst);
        TextView tvGia = findViewById(R.id.tvGia);
        tv.setText(listSP.getName());
        tvGia.setText(listSP.getGia());
>>>>>>> 9b3a8e1 (update)
    }
}