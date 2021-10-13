package com.example.fpt_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.Response2PikModel;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private ImageView imageView2Pik;
    private Button buttonAddNew, btnxoa, buttonchat;

    private List<Product>  data = new ArrayList<>();
    private ProductAdapter adapter;
    private ArrayAdapter<String> arrayAdapter;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";

    private AccessTokenManager tokenManager;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        buttonAddNew = findViewById(R.id.buttonAddNew);
        buttonchat =  findViewById(R.id.buttonChat);
        imageView2Pik= (ImageView) findViewById(R.id.thongtin);
        searchView = findViewById(R.id.searchview);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listViewProducts.setAdapter(arrayAdapter);
        setTitle("Home");

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

        imageView2Pik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductActivity.this, ThontinActivity.class);
                startActivity(i);


            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ProductActivity.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ProductActivity.this.arrayAdapter.getFilter().filter(newText);
                return false;
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