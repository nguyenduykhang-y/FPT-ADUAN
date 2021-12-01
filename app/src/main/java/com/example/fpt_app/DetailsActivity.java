package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Adapter.CategoryAdapter;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Like;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ProductCategory;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private ImageView img,like;

    private TextView tv, tvGia;
    private Button btn;
    private Integer product_id = -1;
    private TextView tvCategory_id,tvQuantity;
    private Button btnADDGH;
    private List<ProductCategory> data;

    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private String img_url= null;
    private Integer category_id = 0;
    private Integer cart_id = -1;
    private Integer idProduct =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //ánh xạ
        img = (ImageView)findViewById(R.id.imgdetail);
         tv = findViewById(R.id.tvTesst);
         btn = findViewById(R.id.mua);
         tvGia = findViewById(R.id.tvGia);
        tvCategory_id = findViewById(R.id.tvCategoryID);
        tvQuantity= findViewById(R.id.tvQuantity);
        btnADDGH= findViewById(R.id.addtoGio);


        //get từ adapter qua
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
        img_url = getIntent().getStringExtra("imgesview");
        Glide.with(getBaseContext()).load(img_url).into(img);
        tv.setText(getIntent().getStringExtra("name"));
        tvGia.setText(decimalFormat.format(Integer.parseInt(getIntent().getStringExtra("price")))+" VNĐ");
        tvQuantity.setText("Số Lượng: "+ getIntent().getStringExtra("quantity"));
        idProduct=Integer.parseInt((getIntent().getStringExtra("id")));

        IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service1.productCategoryGetAll().enqueue(getAllProductCategoryCB);

        btnADDGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                cart.setImage_url(img_url);
                cart.setIdProduct(idProduct);
                cart.setName(tv.getText().toString());
                cart.setPrice(Double.parseDouble(getIntent().getStringExtra("price")));
                cart.setCategory_id(Integer.parseInt(getIntent().getStringExtra("category_id")));
                cart.setQuantity(Integer.parseInt(getIntent().getStringExtra("quantity")));

                IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                service1.CartInsert(cart).enqueue(insert_cart);

            }
        });


    }
    public void onCustomToggleClick(View view) {
        Like cart = new Like();
        cart.setImage_url(img_url);
        cart.setIdProduct(idProduct);
        cart.setName(tv.getText().toString());
        cart.setPrice(Double.parseDouble(getIntent().getStringExtra("price")));
        cart.setCategory_id(Integer.parseInt(getIntent().getStringExtra("category_id")));
        cart.setQuantity(Integer.parseInt(getIntent().getStringExtra("quantity")));
        IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service1.LikeInsert(cart).enqueue(insert_like);

    }
    Callback<ResponseModel> insert_cart = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    Toast.makeText(DetailsActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(">>>>>insertCB getStatus failed", "insert failed");
                }
            } else{
                Log.e(">>>>>insertCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e(">>>>>insertCB onFailure", t.getMessage());
        }
    };
    Callback<ResponseModel> insert_like = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    Toast.makeText(getApplicationContext(), "Đã thích sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(">>>>>insertCB getStatus failed", "insert failed");
                }
            } else{
                Log.e(">>>>>insertCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {
            Log.e(">>>>>insertCB onFailure", t.getMessage());
        }
    };

    Callback<List<ProductCategory>> getAllProductCategoryCB = new Callback<List<ProductCategory>>() {
        @Override
        public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
            if (response.isSuccessful()){
                data = response.body();
                List<ProductCategory> cate =data;
                int id_ca = Integer.parseInt(getIntent().getStringExtra("category_id"));
                for (ProductCategory b: cate){
                    if (id_ca == b.getId()){
                        tvCategory_id.setText("Thể loại: "+b.getName());
                    }
                }

            } else {
                Log.e("getAllProductCategoryCB onResponse: ", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<ProductCategory>> call, Throwable t) {
            Log.e("getAllProductCategoryCB onResponse: ", t.getMessage());
        }
    };
}