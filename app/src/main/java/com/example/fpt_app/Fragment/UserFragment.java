package com.example.fpt_app.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fpt_app.Activity_Register_Shop;

import com.example.fpt_app.Adapter.LikeAdapter;
import com.example.fpt_app.DetailsActivity;

import com.example.fpt_app.FogotActivity;
import com.example.fpt_app.LoginActivity;
import com.example.fpt_app.Models.AccessToken;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Like;
import com.example.fpt_app.Models.Person;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.Models.User;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;
import com.example.fpt_app.OderCTActivity;
import com.example.fpt_app.ProductActivity;
import com.example.fpt_app.R;
import com.example.fpt_app.RegisterActivity;
import com.example.fpt_app.Activity_Register_Shop;
import com.example.fpt_app.SPLikeActivity;
import com.example.fpt_app.ThongkeActivity;
import com.example.fpt_app.UserInsertActivity;
import com.example.fpt_app.UserSettingActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment  {
    private String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    Button btnout;
    private Switch aSwitch;
    private ImageView Setting, ivUserInsert,imgOderCt,imgThongke;
    private TextView tvName, tvEmail,tvShop,tvFavNum;
    UserFragment context;
    String name, email, phone, emailshop;
    private List<Like> data = new ArrayList<>();
    private List<Shop> shopList = new ArrayList<>();




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
        tvFavNum= v.findViewById(R.id.FavNum);
        imgOderCt=v.findViewById(R.id.imageViewDelivering);
        imgThongke=v.findViewById(R.id.imageThongke);
        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserSettingActivity.class);
                startActivity(i);
            }
        });


        imgOderCt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), OderCTActivity.class);
                startActivity(i);
            }
        });
        imgThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ThongkeActivity.class);
                startActivity(i);
            }
        });
        ivUserInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shop shop = new Shop();
                shop.setStoreEmail(email);
                service.getStoreByEmail(shop).enqueue(getShopbyEmail);
//                for (Shop s:shopList ){
//                    if (email.equals(s.getStoreEmail())){
//                        Intent intent = new Intent(getContext(), UserInsertActivity.class );
//                        startActivity(intent);
//                    }else {
//                        Registration_Confirmation(Gravity.CENTER);
//                    }
//                }
            }
        });




        service.Profile().enqueue(getProfile);
        service.LikeGetALL().enqueue(getALLlike);
        service.getAllShop().enqueue(getAllShop);
        return  v;
    }

    private void Registration_Confirmation(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_registration_confirmation);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }

        Button btnAccept = dialog.findViewById(R.id.btnAccept);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Activity_Register_Shop.class);
                Bundle bundle = new Bundle();
                bundle.putString("email",email);
                bundle.putString("phone", phone);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    Callback<List<Shop>> getAllShop = new Callback<List<Shop>>() {
        @Override
        public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
            shopList = response.body();
        }

        @Override
        public void onFailure(Call<List<Shop>> call, Throwable t) {

        }
    };

    Callback<ResponseModel> getShopbyEmail = new Callback<ResponseModel>() {
        @Override
        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            if (response.isSuccessful()){
                ResponseModel model = response.body();
                if (model.getStatus()){
                    Intent intent = new Intent(getContext(), UserInsertActivity.class );
                    startActivity(intent);
                }else {

                }
            }else{
                Log.e(">>>>>insertCB onResponse", response.message());
                Registration_Confirmation(Gravity.CENTER);
            }


        }

        @Override
        public void onFailure(Call<ResponseModel> call, Throwable t) {

        }
    };


    Callback<User> getProfile = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()){
                User u =  new User();
                u = response.body();
                name = u.getName();
                email = u.getEmail();
                phone = u.getPhone();
                tvName.setText(name);
                tvEmail.setText(u.getEmail());

//                ivUserInsert.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        User u =  new User();
//                        u = response.body();
//                        if (u.getRoles().equals("1")){
//                            Registration_Confirmation(Gravity.CENTER);
//
//                        }else if(u.getRoles().equals("2")){
//
//                            Intent intent =  new Intent(getContext(), UserInsertActivity.class);
//
//
//                            startActivity(intent);
//                        }
//                    }
//                });

//
//                if (u.getRoles().equals("2")){
//                    ivUserInsert.setVisibility(View.VISIBLE);
//                    tvShop.setVisibility(View.VISIBLE);
//                }


            } else {
                Log.e(">>>>>", response.message());
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            Log.e(">>>>>", t.getMessage());
        }
    };

    Callback<List<Like>> getALLlike = new Callback<List<Like>>() {
        @Override
        public void onResponse(Call<List<Like>> call, Response<List<Like>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();


                    List<Like>likes= data;
                    if(likes != null){
                        tvFavNum.setText(String.valueOf(likes.size()));
                    }else {
                        tvFavNum.setText("0");
                    }


                } else {
                    data.clear();
                    data.addAll(response.body());

                }
            } else {
                Log.e(">>>>>getAllCB onResponse", response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Like>> call, Throwable t) {
            Log.e(">>>>>getAllCB onFailure", t.getMessage());
        }
    };
}