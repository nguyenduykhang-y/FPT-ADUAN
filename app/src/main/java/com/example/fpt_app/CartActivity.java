package com.example.fpt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpt_app.Adapter.CartAdapter;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Oder;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private RecyclerView mRecycle;
    private List<Cart> data = new ArrayList<>();
    private TextView txtGiaTien;
    private CartAdapter adapter;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private AccessTokenManager tokenManager;
    Button btnThanhToan;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //ánh xạ
        mRecycle = findViewById(R.id.lvCart);
        txtGiaTien = findViewById(R.id.TvGiaTien);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        mRecycle.setLayoutManager(gridLayoutManager);

        btnThanhToan= findViewById(R.id.btnThanhtoan);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));



        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.CartGetALL().enqueue(getALLCart);

        Log.d("soluong", String.valueOf(getIntent().getStringExtra("soluong")));



        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Cart> cartList = ((CartAdapter) mRecycle.getAdapter()).getData();
                for (Cart cart : cartList){

                }

            }
        });



    }



    private Context getCt(){
        return this;
    }

    Callback<List<Cart>> getALLCart = new Callback<List<Cart>>() {
        @Override
        public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    adapter = new CartAdapter(getCt(), data);

                    double sum = 0;
                    List<Cart> carts;
                    carts = data;

                    if(carts != null){
                        for (Cart c : carts){
//                        double so = (double) (c.getPrice()* Double.parseDouble(getIntent().getStringExtra("soluong")));
                            sum += c.getPrice();
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
                        txtGiaTien.setText(decimalFormat.format(sum)+" VNĐ");
                    }

                    mRecycle.setAdapter(adapter);

                } else {
                    data.clear();
                    data.addAll(response.body());
                    mRecycle.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Log.e(">>>>>getAllCart onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Cart>> call, Throwable t) {
            Log.e(">>>>>getAllCart onFailure", t.getMessage());
        }
    };



}