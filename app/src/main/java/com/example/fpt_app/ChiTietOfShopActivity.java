package com.example.fpt_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietOfShopActivity extends AppCompatActivity {
    private ImageView img;

    private TextView textViewShopName, StoreAddress;
    private Integer product_id = -1;
    private TextView tvCategory_id,tvQuantity;
    private Button btnADDGH;


    private static String BASE_URL = "http://10.0.3.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private String img_url= null;
    private Integer category_id = 0;
    private Integer cart_id = -1;
    private Integer idProduct =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_shop);

        //ánh xạ
        img = (ImageView)findViewById(R.id.imgdetail);
        textViewShopName = findViewById(R.id.name);
        StoreAddress = findViewById(R.id.StoreAddress);


        img_url = getIntent().getStringExtra("imgShop");
        Glide.with(getBaseContext()).load(img_url).into(img);
        textViewShopName.setText(getIntent().getStringExtra("name"));
        StoreAddress.setText(getIntent().getStringExtra("address"));




    }
}