package com.example.fpt_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fpt_app.Models.AccessToken;
import com.example.fpt_app.Models.AccessTokenManager;


public class ThontinActivity extends AppCompatActivity {
    private String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    Button btnout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thontin);
        btnout = findViewById(R.id.btnlogout);
        setTitle("Info");
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        AccessToken token = tokenManager.getToken();

//        if (token.getAccess_token()!=null){
//            startActivity(new Intent(getBaseContext(), ProductActivity.class));
//            finish();
//        }
        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenManager.deleteToken();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                Toast.makeText(ThontinActivity.this, "Logout Suscess", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }



}