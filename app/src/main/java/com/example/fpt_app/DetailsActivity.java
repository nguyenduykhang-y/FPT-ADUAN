package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

public class DetailsActivity extends AppCompatActivity {
    private ImageView img;
    private TextView tv, tvGia,tvCategory_id,tvQuantity;
    private Button btn;
    private Integer product_id = -1;
    private static String BASE_URL = "http://10.0.3.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        img = (ImageView)findViewById(R.id.imgdetail);
         tv = findViewById(R.id.tvTesst);
         btn = findViewById(R.id.mua);
         tvGia = findViewById(R.id.tvGia);
        tvCategory_id = findViewById(R.id.tvCategoryID);
        tvQuantity= findViewById(R.id.tvQuantity);

        img.setImageResource(getIntent().getIntExtra("imgesview",0));
        tv.setText(getIntent().getStringExtra("name"));
        tvGia.setText(getIntent().getStringExtra("price"));
        tvCategory_id.setText("Mã Loại: "+getIntent().getStringExtra("category_id"));
        tvQuantity.setText("Số lượng: "+getIntent().getStringExtra("quantily"));


    }

}