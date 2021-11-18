package com.example.fpt_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietOfShopActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ImageView img;
    private RecyclerView recyclerViewProducts;
    private TextView textViewShopName, StoreAddress;
    private Integer product_id = -1;
    private List<Product> data = new ArrayList<>();
    public ProductAdapter adapter;
    private SearchView searchView;
    private static String BASE_URL = "http://10.0.3.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private String img_url= null;
    private AccessTokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_shop);

        //ánh xạ
        img = (ImageView)findViewById(R.id.imgdetail);
        textViewShopName = findViewById(R.id.name);
        StoreAddress = findViewById(R.id.StoreAddress);
        recyclerViewProducts = findViewById(R.id.rcv_shopProduct);
        searchView = findViewById(R.id.search);
        img_url = getIntent().getStringExtra("imgShop");
        Glide.with(getBaseContext()).load(img_url).into(img);
        textViewShopName.setText(getIntent().getStringExtra("name"));
        StoreAddress.setText(getIntent().getStringExtra("address"));

        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        //2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerViewProducts.setLayoutManager(gridLayoutManager);

        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext());
        recyclerViewProducts.setAdapter(productAdapter);

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.productGetAll().enqueue(getAllCB);
        initListener();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume: ", "onResume>>>>");
        IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service.productGetAll().enqueue(getAllCB);

    }
    Callback<List<Product>> getAllCB = new Callback<List<Product>>() {
        @Override
        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    adapter = new ProductAdapter(data, getBaseContext());
                    recyclerViewProducts.setAdapter(adapter);
                } else {
                    data.clear();
                    data.addAll(response.body());
                    recyclerViewProducts.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Log.e(">>>>>getAllCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Product>> call, Throwable t) {
            Log.e(">>>>>getAllCB onFailure", t.getMessage());
        }
    };
    private void initListener(){
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }
}