package com.example.fpt_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.User;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfomationActivity extends AppCompatActivity {

    private ImageView ic_back, ivToPassword;
    private String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    private TextView name, email, phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        name = findViewById(R.id.edtName);
        email = findViewById(R.id.tvEmail);
        phone = findViewById(R.id.tvPhone);
        ic_back = findViewById(R.id.ivBack);
        ivToPassword = findViewById(R.id.ivToPassword);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserInfomationActivity.this, UserSettingActivity.class);
                startActivity(i);
            }
        });
        ivToPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserInfomationActivity.this, FogotActivity.class);
                startActivity(i);
            }
        });
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserInfomationActivity.this, UserSettingActivity.class);
                startActivity(i);
            }
        });
        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.Profile().enqueue(getProfile);

    }

    Callback<User> getProfile = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()){
                User u = new User();
                u = response.body();
                name.setText(u.getName());
                email.setText(u.getEmail());
                phone.setText(u.getPhone());
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
