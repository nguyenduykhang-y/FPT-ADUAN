package com.example.fpt_app.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ProductFragment extends Fragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private Button btnA, btnB, btnC;

    private List<Product>  data = new ArrayList<>();
    ProductAdapter productAdapter;
    private static String BASE_URL = "http://10.0.2.2:8081/";
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

        mRecyclerView= view.findViewById(R.id.rcv_list);
        gridLayoutManager = new GridLayoutManager(getContext(), 2 );
        mRecyclerView.setLayoutManager(gridLayoutManager);

        tokenManager = AccessTokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE));

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);
        service.productGetAll().enqueue(getAllCB);

        return view;
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
                scrollToItem( 5);
            case R.id.btnGM:
                scrollToItem(10);
        }
    }

    private void scrollToItem(int i) {
        if (gridLayoutManager == null){
            return;
        }
        gridLayoutManager.scrollToPositionWithOffset(i, 0);
    }
}