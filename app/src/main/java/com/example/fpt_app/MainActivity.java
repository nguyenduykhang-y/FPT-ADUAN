package com.example.fpt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.shop);
       bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId())
               {
                   case R.id.shop:
                       return true;
                   case R.id.cart:
                       startActivity(new Intent(getApplicationContext(), CartActivity.class));
                       overridePendingTransition(0, 0);
                       return true;
                   case R.id.home:
                       startActivity(new Intent(getApplicationContext(), ProductActivity.class));
                       overridePendingTransition(0, 0);
                       return true;
                   case R.id.noti:
                       startActivity(new Intent(getApplicationContext(), NotificaitonActivity.class));
                       overridePendingTransition(0, 0);
                       return true;
                   case R.id.user:
                       startActivity(new Intent(getApplicationContext(), ThontinActivity.class));
                       overridePendingTransition(0, 0);
                       return true;
               }
               return false;
           }
       });

    }
}