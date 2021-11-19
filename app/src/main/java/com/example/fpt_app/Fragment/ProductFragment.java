package com.example.fpt_app.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.fpt_app.Adapter.ListAdapter;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ProductFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener{

    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private Button btnA, btnB, btnC;
    public SearchView searchView;

    private List<Product>  data = new ArrayList<>();
    public ProductAdapter productAdapter;
    private static String BASE_URL = "http://10.0.3.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private AccessTokenManager tokenManager;
    public ProductFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_product, container, false);

       //ánh xạ
        btnA = view.findViewById(R.id.btnAsus);
        btnB = view.findViewById(R.id.btnHP);
        btnC = view.findViewById(R.id.btnGM);
        searchView = view.findViewById(R.id.search);
        mRecyclerView= view.findViewById(R.id.rcv_list);
        gridLayoutManager = new GridLayoutManager(getContext(), 2 );
        mRecyclerView.setLayoutManager(gridLayoutManager);

        tokenManager = AccessTokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE));

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);
        service.productGetAll().enqueue(getAllCB);
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        initListener();
        return view;
    }

    private void initListener() {
        searchView.setOnQueryTextListener(this);
    }

    //call product
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAsus:
                scrollToItem(0);
            case R.id.btnHP:
                scrollToItem( 2);
            case R.id.btnGM:
                scrollToItem(4);
        }
    }

    private void scrollToItem(int i) {
        if (gridLayoutManager == null){
            return;
        }
        gridLayoutManager.scrollToPositionWithOffset(i, 0);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Product> productList = this.filter(newText);
        productAdapter = new ProductAdapter(productList, getContext());
        mRecyclerView.setAdapter(productAdapter);
        return false;
    }
    @Override
    public void onPause() {
        super.onPause();

    }
    public List<Product> filter(String strSearch){
        List<Product> datafilter = data;
        if (strSearch == null ||  strSearch.length() == 0){
           return data;
        }
        else {
                List<Product> collect = datafilter.stream()
                        .filter(i -> i.getName().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                return collect;
        }
    }

}