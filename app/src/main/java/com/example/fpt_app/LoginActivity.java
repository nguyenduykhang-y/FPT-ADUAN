package com.example.fpt_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class LoginActivity extends AppCompatActivity {

    private String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        AccessToken token = tokenManager.getToken();
        if (token.getAccess_token()!=null){
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Button buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
//        TextView fogot = (TextView) findViewById(R.id.fogot);
        TextView dk = (TextView) findViewById(R.id.dangky);
        setTitle("Login");
        dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

//        fogot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getBaseContext(), FogotActivity.class));
//            }
//        });

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String pass = editTextPassword.getText().toString();

                service.login(new Person(email, pass)).enqueue(loginCB);

            }
        });
    }


    Callback<AccessToken> loginCB = new Callback<AccessToken>() {
        @Override
        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
            if (response.isSuccessful()){
                AccessToken token = response.body();
                tokenManager.saveToken(token);
                if (token.getIs_auth()){
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Suscess", Toast.LENGTH_SHORT).show();
                    finish();
                }
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