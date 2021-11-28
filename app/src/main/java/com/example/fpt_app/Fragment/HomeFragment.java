package com.example.fpt_app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fpt_app.Adapter.ListAdapter;
import com.example.fpt_app.Adapter.PhotoViewPager;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Adapter.Silder;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Silder> mSilders;

    private List<Product>  data = new ArrayList<>();
    ProductAdapter productAdapter;
    private static String BASE_URL = "http://10.0.3.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";

    private GridLayoutManager gridLayoutManager;
    RecyclerView mRecyclerView;
    private AccessTokenManager tokenManager;
    public HomeFragment() {
        // Required empty public constructor
    }
    private Handler mhHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager.getCurrentItem() == mSilders.size() - 1){
                mViewPager.setCurrentItem(0);
            }else {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        //ánh xạ
        mViewPager = view.findViewById(R.id.view_images);
        mCircleIndicator  = view.findViewById(R.id.circle_indicatior);
        mSilders = getListPhoto();
        PhotoViewPager adapter = new PhotoViewPager(mSilders);
        mViewPager.setAdapter(adapter);
        mCircleIndicator.setViewPager(mViewPager);
        mRecyclerView = view.findViewById(R.id.Rcv_ListViewProducts);

        //custom rcv
        gridLayoutManager = new GridLayoutManager(getContext(), 2 );
        mRecyclerView.setLayoutManager(gridLayoutManager);


        tokenManager = AccessTokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE));

        //Banner list
        mhHandler.postDelayed(mRunnable, 2100);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mhHandler.removeCallbacks(mRunnable);
                mhHandler.postDelayed(mRunnable, 2100);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);
        service.productGetAll().enqueue(getAllCB);



        return view;

    }
    //callback product
    Callback<List<Product>> getAllCB = new Callback<List<Product>>() {
        @Override
        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    productAdapter = new ProductAdapter(data, getContext());
                    mRecyclerView.setAdapter(productAdapter);
                } else {
                    data.clear();
                    data.addAll(response.body());
                    mRecyclerView.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                }
            } else {
                Log.e(">>>>>getAllCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Product>> call, Throwable t) {
            Log.e(">>>>>getAllCB onFailure", t.getMessage());
        }
    };

    //list image banner
    private List<Silder> getListPhoto() {
        List<Silder> list = new ArrayList<>();
        list.add(new Silder(R.drawable.backgourd));
        list.add(new Silder(R.drawable.backgroud1));
        list.add(new Silder(R.drawable.backgourd2));
        return list;
    }
}