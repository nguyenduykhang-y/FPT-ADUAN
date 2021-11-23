package com.example.fpt_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fpt_app.Fragment.UserFragment;

public class UserSettingActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvBack, tvUserInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        //ánh xạ
        ivBack = findViewById(R.id.ivBackSetting);
        tvBack = findViewById(R.id.tvBackSetting);
        tvUserInfo = findViewById(R.id.tvUserInfo);

        tvUserInfo.setClickable(true);
        //onClick

//        tvUserInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(UserSettingActivity.this, UserInfomationActivity.class);
//                startActivity(i);
//            }
//        });

//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new UserFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.flUserSetting, fragment).commit();
//            }
//        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSettingActivity.this,UserFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });


    }
}
