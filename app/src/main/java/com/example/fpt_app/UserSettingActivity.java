package com.example.fpt_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fpt_app.Fragment.UserFragment;
import com.example.fpt_app.Models.AccessToken;
import com.example.fpt_app.Models.AccessTokenManager;

public class UserSettingActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvBack, tvUserInfo,tvFavProduct;
    private String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    private Button btnout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        //ánh xạ
        ivBack = findViewById(R.id.ivBackSetting);
        tvUserInfo = findViewById(R.id.tvUserInfo);
        tvFavProduct = findViewById(R.id.tvFavProduct);
        btnout = findViewById(R.id.user_logout);
        tvUserInfo.setClickable(true);

        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        AccessToken token = tokenManager.getToken();

        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenManager.deleteToken();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                Toast.makeText(UserSettingActivity.this, "Logout Suscess", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        //onClick

        tvUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserSettingActivity.this, UserInfomationActivity.class);
                startActivity(i);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSettingActivity.this,UserFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
        tvFavProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserSettingActivity.this,SPLikeActivity.class);
                startActivity(i);
            }
        });

    }
}
