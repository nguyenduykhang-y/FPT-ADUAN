package com.example.fpt_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.fpt_app.Adapter.PhotoViewPager;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Adapter.Silder;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.Response2PikModel;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Silder> mSilders;

    private ListView listViewProducts;

    private Button buttonAddNew, btnxoa, buttonchat, btnChuyen;

    private List<Product>  data = new ArrayList<>();
    private ProductAdapter adapter;
    private ArrayAdapter<String> arrayAdapter;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";

    private AccessTokenManager tokenManager;
    private SearchView searchView;
    private Handler mhHandler = new Handler();
    TextView textView;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager.getCurrentItem() == mSilders.size() - 1){
                mViewPager.setCurrentItem(0);
            }else {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        buttonAddNew = findViewById(R.id.buttonAddNew);
        buttonchat =  findViewById(R.id.buttonChat);

        btnChuyen =  findViewById(R.id.btnChuyen);
        textView = findViewById(R.id.marquee);

        textView.setSelected(true);

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listViewProducts.setAdapter(arrayAdapter);
        setTitle("Home");

        mViewPager = findViewById(R.id.view_images);
        mCircleIndicator  = findViewById(R.id.circle_indicatior);
        mSilders = getListPhoto();

        PhotoViewPager adapter = new PhotoViewPager(mSilders);
        mViewPager.setAdapter(adapter);
        mCircleIndicator.setViewPager(mViewPager);

        mhHandler.postDelayed(mRunnable, 2100);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mhHandler.removeCallbacks(mRunnable);
                mhHandler.postDelayed(mRunnable, 2100);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.shop:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:

                        return true;
                    case R.id.noti:
                        startActivity(new Intent(getApplicationContext(), NotificaitonActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), ThontinActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.productGetAll().enqueue(getAllCB);

        buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ProductFormActivity.class));
            }
        });
        buttonchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SocketActivity.class));
            }
        });
        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product p = (Product) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getBaseContext(), ProductFormActivity.class);
                intent.putExtra("id", p.getId());
                startActivity(intent);
            }
        });

        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product p = (Product) adapterView.getItemAtPosition(i);

                XacNhanXoa(p);

           return true;
            }
        });

        btnChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductActivity.this, ListSPActivity.class);
                startActivity(i);


            }
        });



    }

    private List<Silder> getListPhoto() {
        List<Silder> list = new ArrayList<>();
        list.add(new Silder(R.drawable.backgourd));
        list.add(new Silder(R.drawable.backgroud1));
        list.add(new Silder(R.drawable.backgourd2));
        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mhHandler.removeCallbacks(mRunnable);
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

            service.productDelete(p).enqueue(deleteCB);
            Toast.makeText(ProductActivity.this, "Suscess!", Toast.LENGTH_SHORT).show();

        }
    });
    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });

    alertDialog.show();

}

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume: ", "onResume>>>>");
        IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service.productGetAll().enqueue(getAllCB);
        mhHandler.postDelayed(mRunnable, 2500);
    }

    Callback<ResponseModel> deleteCB = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    IRetrofitService service = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                    service.productGetAll().enqueue(getAllCB);
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

    Callback<Response2PikModel> uploadCB = new Callback<Response2PikModel>() {
        @Override
        public void onResponse(Call<Response2PikModel> call, Response<Response2PikModel> response) {
            if (response.isSuccessful()){
                Response2PikModel model = response.body();
                Log.e(">>>>>uploadCB getSaved", model.getSaved());
            } else{
                Log.e(">>>>>uploadCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<Response2PikModel> call, Throwable t) {
            Log.e(">>>>>uploadCB onFailure", t.getMessage());
        }
    };

    Callback<List<Product>> getAllCB = new Callback<List<Product>>() {
        @Override
        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    adapter = new ProductAdapter(data, getBaseContext());
                    listViewProducts.setAdapter(adapter);
                } else {
                    data.clear();
                    data.addAll(response.body());
                    listViewProducts.setAdapter(adapter);
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
}