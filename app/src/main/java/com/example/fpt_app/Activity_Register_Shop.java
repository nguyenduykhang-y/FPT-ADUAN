package com.example.fpt_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.Response2PikModel;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Register_Shop extends AppCompatActivity {
    private static final int MY_REQUEST_CODE =10;
    EditText edtShopName, edtShopAddress;
    Button btnregister_Shop,btnCancel_Regiter_Shop;
    TextView TakePhoto;
    ImageView imageViewProduct;
    String emailShop, phoneShop;

    private String image_url = null;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);
        AnhXa();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String email = bundle.getString("email", "");
            String phone = bundle.getString("phone","");
            emailShop = email;
            phoneShop = phone;
            Log.d("av", "onCreate: "+emailShop);
            Log.d("av", "onCreate: "+phoneShop);
        }

        TakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermision();
            }
        });

        btnregister_Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shop shop = new Shop();
                // validation
                shop.setStoreImage(image_url);
                Log.d("TAG", "onClick: "+ image_url);

                shop.setStoteName(edtShopName.getText().toString());
                shop.setStoreAddress(edtShopAddress.getText().toString());
                shop.setStoreEmail(emailShop);
                shop.setStorePhone(phoneShop);
                IRetrofitService service1 = new RetrofitBuilder().createService(IRetrofitService.class, BASE_URL);
                service1.StoreInsert(shop).enqueue(insert_store_CB);


            }
        });

        btnCancel_Regiter_Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private ActivityResultLauncher<Intent> mIntentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null ){
                            return;
                        }
                        Uri uri = data.getData();

                        try {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] bytes = baos.toByteArray();
                            String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
                            encoded = "data:image/png;base64," + encoded;

                            MultipartBody.Part part = MultipartBody.Part.createFormData("image", encoded);
                            IRetrofitService service1 = new RetrofitBuilder()
                                    .createService(IRetrofitService.class, BASE_2PIK_URL);

                            service1.upload(part).enqueue(uploadCB);
                            imageViewProduct.setImageBitmap(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    private void onClickRequestPermision(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else {
            String [] permisson = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permisson, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        // pass the constant to compare it
        // with the returned requestCode
        mIntentActivityResultLauncher.launch(Intent.createChooser(i, "Select Picture"));
    }
    //ảnh..///>>>>/
    Callback<Response2PikModel> uploadCB = new Callback<Response2PikModel>() {
        @Override
        public void onResponse(Call<Response2PikModel> call, Response<Response2PikModel> response) {
            if (response.isSuccessful()){
                Response2PikModel model = response.body();
                image_url = model.getSaved();

                Log.d("img", String.valueOf(image_url));

                Glide.with(Activity_Register_Shop.this)
                        .load(image_url)
                        .into(imageViewProduct);
            } else{
                Log.e(">>>>>uploadCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<Response2PikModel> call, Throwable t) {
            Log.e(">>>>>uploadCB onFailure", t.getMessage());
        }
    };
    Callback<ResponseModel> insert_store_CB = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if(model.getStatus()){
                    Toast.makeText(getApplicationContext(), "Suscess", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), UserInsertActivity.class));
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

    public void AnhXa(){
        edtShopName = findViewById(R.id.edtShopName);
        edtShopAddress = findViewById(R.id.edtShopAddress);
        TakePhoto = findViewById(R.id.TakePhoto);
        imageViewProduct = findViewById(R.id.imageViewProduct);
        btnregister_Shop = findViewById(R.id.btnregister_Shop);
        btnCancel_Regiter_Shop = findViewById(R.id.btnCancel_Regiter_Shop);
    }
}