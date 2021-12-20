package com.example.fpt_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
       ImageView backlogins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        edtEmail = findViewById(R.id.edtEmail);
        backlogins = findViewById(R.id.backlogin);
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
        backlogins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FogotActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });
    }
    Callback<AccessToken> forgotCB = new Callback<AccessToken>() {
        @Override
        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
            if (response.isSuccessful()){

                AccessToken token = response.body();
                tokenManager.getToken();

                Toast.makeText(FogotActivity.this, "Send email Suscess", Toast.LENGTH_SHORT).show();
                LayoutInflater layoutInflater = getLayoutInflater();
                View layout = layoutInflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.toast));
                final Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                finish();
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