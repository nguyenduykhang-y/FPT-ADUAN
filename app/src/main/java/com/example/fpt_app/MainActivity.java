package com.example.fpt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fpt_app.Adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
        BottomNavigationView bt_nv;
        ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.ViewPager);
       bt_nv = findViewById(R.id.bottom_nv);

        setUpViewPager();



        bt_nv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Ban chon menu home", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.cart:
                        Toast.makeText(MainActivity.this, "Ban chon menu cart", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.shop:
                        Toast.makeText(MainActivity.this, "Ban chon menu shop", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.noti:
                        Toast.makeText(MainActivity.this, "Ban chon menu noti", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.user:
                        Toast.makeText(MainActivity.this, "Ban chon menu user", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;

            }
        });


    }
    public void setUpViewPager(){
        ViewPagerAdapter viewpagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewpagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bt_nv.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        bt_nv.getMenu().findItem(R.id.cart).setChecked(true);
                        break;
                    case 2:
                        bt_nv.getMenu().findItem(R.id.shop).setChecked(true);
                        break;
                    case 3:
                        bt_nv.getMenu().findItem(R.id.noti).setChecked(true);
                        break;
                    case 4:
                        bt_nv.getMenu().findItem(R.id.user).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}