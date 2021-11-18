package com.example.fpt_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.example.fpt_app.Adapter.EreaAdapter;
import com.example.fpt_app.Adapter.PhotoViewPager;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Adapter.Silder;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Erea;
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

public class ProductActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerViewProducts;
    private Button buttonAddNew, buttonchat, btnChuyen;
    private List<Product>  data = new ArrayList<>();
    private ProductAdapter adapter;
    private SearchView searchView;
    private EreaAdapter ereaAdapter;
    private Spinner spinner;
    private ArrayAdapter<String> arrayAdapter;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private AccessTokenManager tokenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        recyclerViewProducts = findViewById(R.id.listViewProducts);
        //2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerViewProducts.setLayoutManager(gridLayoutManager);

        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext());
        recyclerViewProducts.setAdapter(productAdapter);


        buttonAddNew = findViewById(R.id.buttonAddNew);
        buttonchat =  findViewById(R.id.buttonChat);
        searchView = findViewById(R.id.search);
        btnChuyen =  findViewById(R.id.btnChuyen);
        spinner = findViewById(R.id.spinner_erea);
        initListener();


        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.productGetAll().enqueue(getAllCB);

        ereaAdapter = new EreaAdapter(this, R.layout.item_selected, getListErea());
        spinner.setAdapter(ereaAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                Toast.makeText(ProductActivity.this,ereaAdapter.getItem(i).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


//        recyclerViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Product p = (Product) adapterView.getItemAtPosition(i);
//                Intent intent = new Intent(getBaseContext(), ProductFormActivity.class);
//                intent.putExtra("id", p.getId());
//                startActivity(intent);
//            }
//        });
//
//        recyclerViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Product p = (Product) adapterView.getItemAtPosition(i);
//
//                XacNhanXoa(p);
//
//           return true;
//            }
//        });

        btnChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductActivity.this, ListSPActivity.class);
                startActivity(i);


            }
        });



    }

    private List<Erea> getListErea() {
        List<Erea> list = new ArrayList<>();
        list.add(new Erea("Tp.Hồ Chí Minh"));
        list.add(new Erea("Quận 1"));
        list.add(new Erea("Quận 2"));
        list.add(new Erea("Quận 3"));
        list.add(new Erea("Quận 4"));
        list.add(new Erea("Quận 5"));
        list.add(new Erea("Quận 6"));
        list.add(new Erea("Quận 7"));

        return list;

    }

    private void initListener(){
        searchView.setOnQueryTextListener(this);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        adapter.filter(newText);
        return false;
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();

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

}