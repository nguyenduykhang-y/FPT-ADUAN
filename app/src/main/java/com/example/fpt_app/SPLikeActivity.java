package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fpt_app.Adapter.CartAdapter;
import com.example.fpt_app.Adapter.LikeAdapter;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Fragment.UserFragment;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Like;
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

public class SPLikeActivity extends AppCompatActivity {
    private ImageView ivBack;
    private RecyclerView mRecycle;
    private List<Like> data = new ArrayList<>();
    private TextView txtGiaTien;
    private LikeAdapter adapter;
    private ArrayAdapter<String> arrayAdapter;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private AccessTokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splike);
        //ánh xạ
        mRecycle = findViewById(R.id.lvCart);
        txtGiaTien = findViewById(R.id.TvGiaTien);
        ivBack = findViewById(R.id.ivBackSetting);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        mRecycle.setLayoutManager(gridLayoutManager);

        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.LikeGetALL().enqueue(getALLlike);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SPLikeActivity.this, UserFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume: ", "onResume>>>>");
        mRecycle.setTooltipText("aaa");

    }
    Callback<List<Like>> getALLlike = new Callback<List<Like>>() {
        @Override
        public void onResponse(Call<List<Like>> call, Response<List<Like>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                        data = response.body();
                        adapter = new LikeAdapter(getBaseContext(), data);
                        mRecycle.setAdapter(adapter);

                } else {
                    data.clear();
                    data.addAll(response.body());
                    mRecycle.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Log.e(">>>>>getAllCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Like>> call, Throwable t) {
            Log.e(">>>>>getAllCB onFailure", t.getMessage());
        }
    };


}