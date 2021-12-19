package com.example.fpt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpt_app.Adapter.CartAdapter;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Oder;
import com.example.fpt_app.Models.OderCT;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.Models.User;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    EditText quantity;
    Button btnThanhToan;
    String name;
    int id;
    Button btnCancle ,btnOke;
    Calendar calendar;
    EditText edtQuantity,edtAddress,edtDate;
    int userId;
    String phone, nameuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //ánh xạ
        mRecycle = findViewById(R.id.lvCart);
        txtGiaTien = findViewById(R.id.TvGiaTien);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        mRecycle.setLayoutManager(gridLayoutManager);
        btnThanhToan = findViewById(R.id.btnThanhtoan);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.CartGetALL().enqueue(getALLCart);
        service.Profile().enqueue(getProfile);


        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.custom_dialog_ct, null);
                alertDialog.setView(dialogView);
                alertDialog.setCancelable(true);
                btnOke = dialogView.findViewById(R.id.btnOke);
                btnCancle= dialogView.findViewById(R.id.btnCancle);
                edtQuantity =dialogView.findViewById(R.id.edtQuantity);
                edtAddress = dialogView.findViewById(R.id.edtAddress);
                edtDate = dialogView.findViewById(R.id.edtDate);
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                calendar = Calendar.getInstance();
                btnOke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
                        String date = simpleDateFormat.format(calendar.getTime());
                        Map<Cart, Integer> cartMap = ((CartAdapter) mRecycle.getAdapter()).getCartMap();
                        String orderId = UUID.randomUUID().toString();
                        for (Map.Entry<Cart, Integer> e : cartMap.entrySet()) {
                            Log.d("TAG", "onClick: " + orderId);
                            OderCT oderCT = new OderCT();
                            oderCT.setOderctId(orderId);
                            oderCT.setUserId(userId);
                            oderCT.setProductId(e.getKey().getIdProduct());
                            oderCT.setQuantity(e.getValue());
                            oderCT.setPrice(e.getKey().getPrice());
                            oderCT.setDate(date);
                            oderCT.setAddress(edtAddress.getText().toString());
                            service.insertOderCT(oderCT).enqueue(inserOderCT);

                        }
                        service.cart_delete_all().enqueue(delete_all_cart);
                    dialog.cancel();
                     Intent i = new Intent(CartActivity.this, MainActivity.class);
                     startActivity(i);
                    }
                });
                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });


            }
        });


    }


    private Context getCt() {
        return this;
    }

    Callback<ResponseModel> inserOderCT = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()) {
                ResponseModel model = response.body();
                if (model.getStatus()) {
                    Toast.makeText(CartActivity.this, "Đã  mua hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(">>>>>insertCB getStatus failed", "insert failed");
                }
            } else {
                Log.e(">>>>>insertCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e(">>>>>insertCB onFailure", t.getMessage());
        }
    };
    Callback<ResponseModel> delete_all_cart = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()) {
                ResponseModel model = response.body();
                if (model.getStatus()) {
                    Toast.makeText(CartActivity.this, "dax xoa", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(">>>>>insertCB getStatus failed", "insert failed");
                }
            } else {
                Log.e(">>>>>insertCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e(">>>>>insertCB onFailure", t.getMessage());
        }
    };
    Callback<List<Cart>> getALLCart = new Callback<List<Cart>>() {
        @Override
        public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
            if (response.isSuccessful()) {
                if (data.size() == 0) {
                    data = response.body();
                    adapter = new CartAdapter(getCt(), data);

                    double sum = 0;
                    List<Cart> carts = data;
                    if (carts != null) {
                        for (Cart c : carts) {
//                        double so = (double) (c.getPrice()* Double.parseDouble(getIntent().getStringExtra("soluong")));
                            sum += c.getPrice();
                        }
                    }
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
                    txtGiaTien.setText(decimalFormat.format(sum) + " VNĐ");


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

    Callback<User> getProfile = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {
                User u = new User();
                u = response.body();
                userId = u.getUserId();
                nameuser = u.getName();
                phone = u.getPhone();

//                if (u.getRoles().equals("2")){
//                    ivUserInsert.setVisibility(View.VISIBLE);
//                    tvShop.setVisibility(View.VISIBLE);
//                }

            } else {
                Log.e(">>>>>", response.message());
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            Log.e(">>>>>", t.getMessage());
        }
    };

}