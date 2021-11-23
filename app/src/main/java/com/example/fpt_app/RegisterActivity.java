package com.example.fpt_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.fpt_app.Models.AccessToken;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.User;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullname,edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister;
    int position;
    String email;
    private String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        fullname = findViewById(R.id.fullname);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        setTitle("Register an account");

//        back = findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ds = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(ds);
//            }
//        });


        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full_name = fullname.getText().toString();
                String newEmail = edtEmail.getText().toString();
                String newPassword = edtPassword.getText().toString();
                String confirmNewPassword = edtConfirmPassword.getText().toString();


                service.dangky(new User(full_name, newEmail, newPassword, confirmNewPassword)).enqueue(registerCB);


            }
        });
    }

        Callback<AccessToken> registerCB = new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    AccessToken token = response.body();
                    tokenManager.saveToken(token);
//                    if (token.getIs_auth()) {
//                        startActivity(new Intent(getBaseContext(), IndexActivity.class));
                    Toast.makeText(RegisterActivity.this, "Suscess", Toast.LENGTH_SHORT).show();


                } else {
                    Log.e(">>>>>", response.message());
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.e(">>>>>", t.getMessage());

            }
        };


        public void LognIn (View view){
            Intent ds = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(ds);

        }
    }


