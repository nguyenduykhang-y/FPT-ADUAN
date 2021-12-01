package com.example.fpt_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fpt_app.Adapter.CategoryAdapter;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Adapter.UserInsertAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ProductCategory;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInsertActivity extends AppCompatActivity {
    private RecyclerView mRecycle;
    private FloatingActionButton btnUserProductInsert;
    private ImageView ivBack;
    private List<Product> data = new ArrayList<>();
    private UserInsertAdapter userAdapter;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private AccessTokenManager tokenManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_insert);

        //anh xa
        ivBack = findViewById(R.id.back_icon);
        btnUserProductInsert = findViewById(R.id.btnUserProductInsert);
        mRecycle = findViewById(R.id.lvUserInsert);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mRecycle.setLayoutManager(gridLayoutManager);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));



        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.productGetAll().enqueue(userProductGetAll);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInsertActivity.this, MainActivity.class));
            }
        });
        btnUserProductInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInsertActivity.this, ProductFormActivity.class));
            }
        });
    }

    private void XacNhanXoa(Product p ){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Notification");
        alertDialog.setMessage("Do you want to delete this product?");
        alertDialog.setCancelable(true);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);

                service.productDelete(p).enqueue(userProductDelete);
                Toast.makeText(UserInsertActivity.this, "Suscess!", Toast.LENGTH_SHORT).show();

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();

    }


    Callback<ResponseModel> userProductDelete = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                    service.productGetAll().enqueue(userProductGetAll);
                } else {
                    Log.e(">>>>>deleteCB getStatus failed", "detele failed");
                }
            } else{
                Log.e(">>>>>deleteCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e(">>>>>detele onFailure", t.getMessage());
        }
    };


    Callback<List<Product>> userProductGetAll = new Callback<List<Product>>() {
        @Override
        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    userAdapter = new UserInsertAdapter(data, getBaseContext());
                    mRecycle.setAdapter(userAdapter);
                } else {
                    data.clear();
                    data.addAll(response.body());
                    mRecycle.setAdapter(userAdapter);
                    userAdapter.notifyDataSetChanged();
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
}
