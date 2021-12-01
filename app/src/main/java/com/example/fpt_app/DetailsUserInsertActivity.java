package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ProductCategory;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsUserInsertActivity extends AppCompatActivity {
    private ImageView imgUserProduct;
    private EditText edtUserProductName, edtUserProductPrice, edtUserQuantity;
    private Spinner spnUserCategory;
    private Button btnUpdate, btnCancel;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private String img_url = null;
    private Integer category_id = 0;
    private Integer cart_id = -1;
    private Integer idProduct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_user_insert);

        //ánh xạ
        imgUserProduct = findViewById(R.id.imageViewUserProductEditImage);
        edtUserProductName = findViewById(R.id.editTextUserProductEditName);
        edtUserProductPrice = findViewById(R.id.editTextUserProductEditName);
        edtUserQuantity = findViewById(R.id.editTextUserProductEditQuantity);
        spnUserCategory = findViewById(R.id.spinnerUserCategoriesEdit);
        btnUpdate = findViewById(R.id.buttonUserSaveEdit);
        btnCancel = findViewById(R.id.buttonUserCancelEdit);
        spnUserCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ProductCategory productCategory = (ProductCategory) adapterView.getItemAtPosition(i);
                category_id = productCategory.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product p = new Product();
                IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                service1.productUpdate(p).enqueue(update_user_product);

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //get từ adapter qua
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
        img_url = getIntent().getStringExtra("imgesview");
        Glide.with(getBaseContext()).load(img_url).into(imgUserProduct);
        edtUserProductName.setText(getIntent().getStringExtra("name"));
        edtUserProductPrice.setText(decimalFormat.format(Integer.parseInt(getIntent().getStringExtra("price"))) + " VNĐ");
//        spnUserCategory
        edtUserQuantity.setText(getIntent().getStringExtra("quantity"));
        idProduct = Integer.parseInt((getIntent().getStringExtra("id")));
    }

    Callback<ResponseModel> update_user_product = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    Toast.makeText(DetailsUserInsertActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e(">>>>>updateCB getStatus failed", "insert failed");
                }
            } else{
                Log.e(">>>>>updateCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {

        }
    };
}

