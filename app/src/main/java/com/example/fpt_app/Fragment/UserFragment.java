package com.example.fpt_app.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpt_app.FogotActivity;
import com.example.fpt_app.LoginActivity;
import com.example.fpt_app.Models.AccessToken;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Person;
import com.example.fpt_app.Models.User;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.ProductActivity;
import com.example.fpt_app.R;
import com.example.fpt_app.RegisterActivity;
import com.example.fpt_app.SPLikeActivity;
import com.example.fpt_app.ThontinActivity;
import com.example.fpt_app.UserSettingActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment  {
    private String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    Button btnout;
    private Switch aSwitch;
    private ImageView Setting;
    private TextView tv;

    private List<User> listUser;


    public UserFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_user, container, false);
        Setting = v.findViewById(R.id.iconGH);
        tv = v.findViewById(R.id.userName);
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserSettingActivity.class);
                startActivity(i);
            }
        });
        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);
        User u = new User();

        service.Profile(u).enqueue(getProfile);
//        btnout = v.findViewById(R.id.btnlogout);
//        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
//        AccessToken token = tokenManager.getToken();
//
//        if (token.getAccess_token()!=null){
//            startActivity(new Intent(getActivity(), ProductActivity.class));
//
//        }
//        btnout.setOnClickListener(this);
        return  v;
    }
//
//    @Override
//    public void onClick(View v) {
//        tokenManager.deleteToken();
//        startActivity(new Intent(getActivity(), LoginActivity.class));
//        Toast.makeText(getActivity(), "Logout Suscess", Toast.LENGTH_SHORT).show();
//
//    }
Callback<List<User>> getProfile = new Callback<List<User>>() {
    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if (response.isSuccessful()){

            Log.d("aaaa", listUser.toString());
//            AccessToken token = response.body();
//            tokenManager.getToken();
//
//            Toast.makeText(FogotActivity.this, "Send email Suscess", Toast.LENGTH_SHORT).show();
//
//
        } else {
            Log.e(">>>>>", response.message());
        }
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        Log.e(">>>>>", t.getMessage());
    }
};
}