package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private ImageView img;

    private TextView tv, tvGia;
    private Button btn;
    private Integer product_id = -1;
  private TextView tv, tvGia,tvCategory_id,tvQuantity;
    private Button btn,btnADDGH;


    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private String image_url = null;
    private Integer category_id = 0;
    private Integer cart_id = -1;
    private Integer idProduct =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //ánh xạ
        img = (ImageView)findViewById(R.id.imgdetail);
         tv = findViewById(R.id.tvTesst);
         btn = findViewById(R.id.mua);
         tvGia = findViewById(R.id.tvGia);
        tvCategory_id = findViewById(R.id.tvCategoryID);
        tvQuantity= findViewById(R.id.tvQuantity);
        btnADDGH= findViewById(R.id.addtoGio);

        //get từ adapter qua
        img.setImageResource(getIntent().getIntExtra("imgesview",0));
        tv.setText(getIntent().getStringExtra("name"));
        tvGia.setText(getIntent().getStringExtra("price"));
        tvCategory_id.setText(getIntent().getStringExtra("category_id"));
        tvQuantity.setText(getIntent().getStringExtra("quantity"));
        idProduct=Integer.parseInt((getIntent().getStringExtra("id")));

        btnADDGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                cart.setImage_url(image_url);
                cart.setIdProduct(idProduct);
                cart.setName(tv.getText().toString());
                cart.setPrice(Double.parseDouble(tvGia.getText().toString()));
                cart.setCategory_id(Integer.parseInt(tvCategory_id.getText().toString()));
                cart.setQuantity(Integer.parseInt(tvQuantity.getText().toString()));

                IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                service1.CartInsert(cart).enqueue(insert_cart);
            }
        });
    }
    Callback<ResponseModel> insert_cart = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    Toast.makeText(DetailsActivity.this, "Suscess", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e(">>>>>insertCB getStatus failed", "insert failed");
                }
            } else{
                Log.e(">>>>>insertCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e(">>>>>insertCB onFailure", t.getMessage());
        }
    };
}