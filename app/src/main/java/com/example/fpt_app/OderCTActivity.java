package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.fpt_app.Adapter.CartAdapter;
import com.example.fpt_app.Adapter.OderCTAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.OderCT;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OderCTActivity extends AppCompatActivity {
    private RecyclerView mRecycle;
    private List<OderCT> data = new ArrayList<>();
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    private OderCTAdapter adapter;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_ctactivity);

        mRecycle = findViewById(R.id.rcv_Oder);
        imgBack = findViewById(R.id.back_icon);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        mRecycle.setLayoutManager(gridLayoutManager);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);


        service.OderCTGETALL().enqueue(getALLCart);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
    Callback<List<OderCT>> getALLCart = new Callback<List<OderCT>>() {
        @Override
        public void onResponse(Call<List<OderCT>> call, Response<List<OderCT>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    adapter = new OderCTAdapter(getBaseContext(), data);
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
        public void onFailure(Call<List<OderCT>> call, Throwable t) {
            Log.e(">>>>>getAllCart onFailure", t.getMessage());
        }
    };


}