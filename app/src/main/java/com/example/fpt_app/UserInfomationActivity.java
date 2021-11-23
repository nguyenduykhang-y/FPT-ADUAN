package com.example.fpt_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserInfomationActivity extends AppCompatActivity {
    private TextView tvBackSetting;
    private ImageView ivBackSetting;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        tvBackSetting = findViewById(R.id.tvBackSetting);
        ivBackSetting = findViewById(R.id.ivBackSetting);

        tvBackSetting.setClickable(true);
        ivBackSetting.setClickable(true);

        tvBackSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserInfomationActivity.this, UserSettingActivity.class);
                startActivity(i);
            }
        });
        ivBackSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserInfomationActivity.this, UserSettingActivity.class);
                startActivity(i);
            }
        });

    }
}
