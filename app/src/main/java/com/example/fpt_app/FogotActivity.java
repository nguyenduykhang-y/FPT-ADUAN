package com.example.fpt_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.fpt_app.Models.AccessToken;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Person;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FogotActivity extends AppCompatActivity {
    private String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    EditText edtEmail,editTextPassword;
    Button btnForgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        edtEmail = findViewById(R.id.edtEmail);
        btnForgot = findViewById(R.id.btnForgot);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        edtEmail.setText("test@gmail.com");
        setTitle("Forgot");

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);


        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                service.chandpassword(new Person(email, null)).enqueue(forgotCB);

            }
        });
    }
    Callback<AccessToken> forgotCB = new Callback<AccessToken>() {
        @Override
        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
            if (response.isSuccessful()){

                AccessToken token = response.body();
                tokenManager.getToken();
//
                    Toast.makeText(FogotActivity.this, "Send email Suscess", Toast.LENGTH_SHORT).show();
//
//
            } else {
                Log.e(">>>>>", response.message());
            }
        }

        @Override
        public void onFailure(Call<AccessToken> call, Throwable t) {
            Log.e(">>>>>", t.getMessage());
        }
    };
}