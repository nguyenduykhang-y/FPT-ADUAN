package com.example.fpt_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fpt_app.Adapter.EreaAdapter;
import com.example.fpt_app.Adapter.ViewPagerAdapter;
import com.example.fpt_app.Fragment.HomeFragment;
import com.example.fpt_app.Fragment.NotiFragment;
import com.example.fpt_app.Fragment.ProductFragment;
import com.example.fpt_app.Fragment.ShopFragment;
import com.example.fpt_app.Fragment.UserFragment;
import com.example.fpt_app.Models.Erea;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        BottomNavigationView bt_nv;
        ViewPager viewPager;
        ImageView iconGH;
    private EreaAdapter ereaAdapter;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.ViewPager);
        bt_nv = findViewById(R.id.bottom_nv);
        iconGH = findViewById(R.id.iconGH);
        spinner = findViewById(R.id.spinner_erea);
        setUpViewPager();
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.homeapp));


        bt_nv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentManager fragment1 = getSupportFragmentManager();
                                        fragment1.beginTransaction().add(R.id.ViewPager,homeFragment)
                                                  .commit();
                        Toast.makeText(MainActivity.this, "Ban chon menu home", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.cart:
                        ProductFragment productFragment = new ProductFragment();
                        FragmentManager fragment2 = getSupportFragmentManager();
                                        fragment2.beginTransaction().add(R.id.ViewPager,productFragment).commit();
                        Toast.makeText(MainActivity.this, "Ban chon menu cart", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.shop:
                        ShopFragment shopFragment = new ShopFragment();
                        FragmentManager fragment3 = getSupportFragmentManager();
                                        fragment3.beginTransaction().add(R.id.ViewPager,shopFragment).commit();
                        Toast.makeText(MainActivity.this, "Ban chon menu shop", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.noti:
                        NotiFragment notiFragment = new NotiFragment();
                        FragmentManager fragment4 = getSupportFragmentManager();
                        fragment4.beginTransaction().add(R.id.ViewPager,notiFragment).commit();
                        Toast.makeText(MainActivity.this, "Ban chon menu noti", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.user:
                        UserFragment userFragment = new UserFragment();
                        FragmentManager fragment5 = getSupportFragmentManager();
                        fragment5.beginTransaction().add(R.id.ViewPager,userFragment).commit();
                        Toast.makeText(MainActivity.this, "Ban chon menu user", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;

            }
        });
        ereaAdapter = new EreaAdapter(this, R.layout.item_selected, getListErea());
        spinner.setAdapter(ereaAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                Toast.makeText(MainActivity.this,ereaAdapter.getItem(i).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
            // intent gio hang activity
        iconGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CartActivity.class);
                startActivity(i);
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
    private List<Erea> getListErea() {
        List<Erea> list = new ArrayList<>();
        list.add(new Erea("Tp.Hồ Chí Minh"));
        list.add(new Erea("Quận 1"));
        list.add(new Erea("Quận 2"));
        list.add(new Erea("Quận 3"));
        list.add(new Erea("Quận 4"));
        list.add(new Erea("Quận 5"));
        list.add(new Erea("Quận 6"));
        list.add(new Erea("Quận 7"));

        return list;

    }
}