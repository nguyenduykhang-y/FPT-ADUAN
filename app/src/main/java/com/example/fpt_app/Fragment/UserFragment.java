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
    private ImageView Setting, ivUserInsert;
    private TextView tvName, tvEmail,tvShop;




    public UserFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_user, container, false);
        Setting = v.findViewById(R.id.iconGH);
        tvName = v.findViewById(R.id.userName);
        tvEmail = v.findViewById(R.id.userEmail);
        ivUserInsert = v.findViewById(R.id.ivUserInsert);
        tvShop = v.findViewById(R.id.tvShop);
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserSettingActivity.class);
                startActivity(i);
            }
        });

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);

        service.Profile().enqueue(getProfile);

        return  v;
    }

    Callback<User> getProfile = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()){
                User u =  new User();
                u = response.body();
                tvName.setText(u.getName());
                tvEmail.setText(u.getEmail());
                if (u.getRoles().equals("2")){
                    ivUserInsert.setVisibility(View.VISIBLE);
                    tvShop.setVisibility(View.VISIBLE);
                }
            } else {
                Log.e(">>>>>", response.message());
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            Log.e(">>>>>", t.getMessage());
        }
    };
}