package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Adapter.CategoryAdapter;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Like;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.Models.Oder;
import com.example.fpt_app.Models.OderCT;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ProductCategory;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.Models.User;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private ImageView img,gh;

    private TextView tv, tvGia, tvName;
    private Button btn;
    private Integer product_id = -1;
    private TextView tvCategory_id;
    private Button btnADDGH, mua;
    private List<ProductCategory> data;
    Button btnCancle ,btnOke;
    ImageView btnDate;
    EditText edtQuantity,edtAddress,edtDate;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private String img_url= null;
    private Integer category_id = 0;
    private Integer cart_id = -1;
    private Integer idProduct =0 ;
     int id, userId;
    String phone,nameuser;
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
        btnADDGH= findViewById(R.id.addtoGio);
        gh = findViewById(R.id.iconGH);
        tvName = findViewById(R.id.tvNamSP);
        mua = findViewById(R.id.mua);

        //get từ adapter qua
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.#");
        img_url = getIntent().getStringExtra("imgesview");
        Glide.with(getBaseContext()).load(img_url).into(img);
        tv.setText(getIntent().getStringExtra("name"));
        tvGia.setText(decimalFormat.format(Integer.parseInt(getIntent().getStringExtra("price")))+" VNĐ");
        idProduct=Integer.parseInt((getIntent().getStringExtra("id")));
        tvName.setText(getIntent().getStringExtra("name"));
        mua.setText("Mua ngay " + decimalFormat.format(Integer.parseInt(getIntent().getStringExtra("price"))) + " VNĐ");



        IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service1.productCategoryGetAll().enqueue(getAllProductCategoryCB);

//        int id = getIntent().getIntExtra("idUser")
        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.Profile().enqueue(getProfile);

        btnADDGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cart cart = new Cart();
                cart.setImage_url(img_url);
                cart.setIdProduct(idProduct);
                cart.setName(tv.getText().toString());
                cart.setPrice(Double.parseDouble(getIntent().getStringExtra("price")));
                cart.setCategory_id(Integer.parseInt(getIntent().getStringExtra("category_id")));


                IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                service1.CartInsert(cart).enqueue(insert_cart);


            }
        });
        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsActivity.this,CartActivity.class);
                startActivity(i);
            }
        });
         mua.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//
//            Oder oder = new Oder();
//            oder.setOderId(id);
//            oder.setNameUser(nameuser);
//            oder.setProductID(idProduct);
//            oder.setPhone(phone);
//
//            IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
//            service1.insertOder(oder).enqueue(insert_oder);



            AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getRootView().getContext());
            View dialogView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.custom_dialog_muangay, null);
            alertDialog.setView(dialogView);
            alertDialog.setCancelable(true);
             btnOke = dialogView.findViewById(R.id.btnOke);
            btnCancle= dialogView.findViewById(R.id.btnCancle);
            edtQuantity =dialogView.findViewById(R.id.edtQuantity);
            edtAddress = dialogView.findViewById(R.id.edtAddress);
            btnDate = dialogView.findViewById(R.id.btnDate);
            edtDate = dialogView.findViewById(R.id.edtDate);
            AlertDialog dialog = alertDialog.create();
            dialog.show();

            btnDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date today = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(today);

                    final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                    final int months = cal.get(Calendar.MONTH);
                    final int years = cal.get(Calendar.YEAR);
                    final Calendar calendar = Calendar.getInstance();
                    int date = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(DetailsActivity.this,new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            calendar.set(i,i1,i2);
                            edtDate.setText(simpleDateFormat.format(calendar.getTime()));
                        }
                    },years,months,dayOfWeek);
                    datePickerDialog.show();
                }

            });
                btnOke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String orderId = UUID.randomUUID().toString();
                        OderCT oderCT = new OderCT();
                        oderCT.setOderctId(orderId);
                        oderCT.setUserId(userId);
                        oderCT.setProductId(idProduct);
                        oderCT.setQuantity(Integer.parseInt(edtQuantity.getText().toString()));
                        oderCT.setPrice(Integer.parseInt(getIntent().getStringExtra("price")));
                        oderCT.setDate(edtDate.getText().toString());
                        oderCT.setAddress(edtAddress.getText().toString());
                        service.insertOderCT(oderCT).enqueue(inserOderCT);
                        dialog.cancel();
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
    public void onCustomToggleClick(View view) {
        Like cart = new Like();
        cart.setImage_url(img_url);
        cart.setIdProduct(idProduct);
        cart.setName(tv.getText().toString());
        cart.setPrice(Double.parseDouble(getIntent().getStringExtra("price")));
        cart.setCategory_id(Integer.parseInt(getIntent().getStringExtra("category_id")));
        IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
        service1.LikeInsert(cart).enqueue(insert_like);


    }
    Callback<ResponseModel> insert_cart = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View layout = layoutInflater.inflate(R.layout.custom_toast_cart, (ViewGroup)findViewById(R.id.toast));
                    final Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
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
    Callback<ResponseModel> inserOderCT = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    Toast.makeText(DetailsActivity.this, "Đã  mua hàng", Toast.LENGTH_SHORT).show();
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
    Callback<ResponseModel> insert_oder = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    Toast.makeText(DetailsActivity.this, "Đã  mua hàng", Toast.LENGTH_SHORT).show();
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
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View layout = layoutInflater.inflate(R.layout.custom_toast_like, (ViewGroup)findViewById(R.id.toast));
                    final Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
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

    Callback<User> getProfile = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()){
                User u =  new User();
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