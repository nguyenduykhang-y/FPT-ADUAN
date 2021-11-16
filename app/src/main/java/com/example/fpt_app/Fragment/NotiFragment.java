package com.example.fpt_app.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fpt_app.Adapter.ProductAdapter;
import com.example.fpt_app.Adapter.ThongbaoAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.TB;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class NotiFragment extends Fragment {
    private GridLayoutManager gridLayoutManager;
    private RecyclerView mRecyclerView;
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private List<TB> data = new ArrayList<>();
    ThongbaoAdapter tbAdapter;
    private AccessTokenManager tokenManager;
    public NotiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noti, container, false);
        mRecyclerView= view.findViewById(R.id.RCV_TB);

        tokenManager = AccessTokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE));

        gridLayoutManager = new GridLayoutManager(getContext(), 1 );
        mRecyclerView.setLayoutManager(gridLayoutManager);

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);
        service.TBgetALLL().enqueue(getAllTB);



        return view;
    }
    Callback<List<TB>> getAllTB = new Callback<List<TB>>() {
        @Override
        public void onResponse(Call<List<TB>> call, Response<List<TB>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    tbAdapter = new ThongbaoAdapter(getContext(), data);
                    mRecyclerView.setAdapter(tbAdapter);
                } else {
                    data.clear();
                    data.addAll(response.body());
                    mRecyclerView.setAdapter(tbAdapter);
                    tbAdapter.notifyDataSetChanged();
                }
            } else {
                Log.e(">>>>>getAllCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<TB>> call, Throwable t) {
            Log.e(">>>>>getAllCB onFailure", t.getMessage());
        }
    };
}