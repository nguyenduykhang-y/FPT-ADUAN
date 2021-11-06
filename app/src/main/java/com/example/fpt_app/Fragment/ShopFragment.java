package com.example.fpt_app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.fpt_app.Adapter.ListAdapter;
import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Adapter.ShopAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.ListSP;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.Response2PikModel;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.ProductFormActivity;
import com.example.fpt_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ShopFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<Shop>  data = new ArrayList<>();
    private ShopAdapter shopAdapter;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private static String BASE_2PIK_URL = "https://2.pik.vn/";
    private ArrayAdapter<String> arrayAdapter;
    private AccessTokenManager tokenManager;
    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        tokenManager = AccessTokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE));


        mRecyclerView= view.findViewById(R.id.Rcv_ListViewProducts);
        gridLayoutManager = new GridLayoutManager(getContext(), 2 );
        mRecyclerView.setLayoutManager(gridLayoutManager);

//        ShopAdapter shopAdapter = new ShopAdapter(arrayAdapter);
//        mRecyclerView.setAdapter(shopAdapter);

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.getAllShop().enqueue(getALLShop);

       return  view;
    }


    //callback shop
    Callback<List<Shop>> getALLShop = new Callback<List<Shop>>() {
    @Override
    public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
        if (response.isSuccessful()){
            if (data.size() == 0){
                data = response.body();
                shopAdapter = new ShopAdapter(getContext(),data);
                mRecyclerView.setAdapter(shopAdapter);
            } else {
                data.clear();
                data.addAll(response.body());
                mRecyclerView.setAdapter(shopAdapter);
                shopAdapter.notifyDataSetChanged();
            }
        } else {
            Log.e(">>>>>getAllCB onResponse", response.message());
        }
    }

    @Override
    public void onFailure(Call<List<Shop>> call, Throwable t) {
        Log.e(">>>>>getAllCB onFailure", t.getMessage());
    }
    };
//    Callback<Response2PikModel> uploadCB = new Callback<Response2PikModel>() {
//        @Override
//        public void onResponse(Call<Response2PikModel> call, Response<Response2PikModel> response) {
//            if (response.isSuccessful()){
//                Response2PikModel model = response.body();
//                Log.e(">>>>>uploadCB getSaved", model.getSaved());
//            } else{
//                Log.e(">>>>>uploadCB onResponse", response.message());
//            }
//        }
//
//        @Override
//        public void onFailure(Call<Response2PikModel> call, Throwable t) {
//            Log.e(">>>>>uploadCB onFailure", t.getMessage());
//        }
//    };
}