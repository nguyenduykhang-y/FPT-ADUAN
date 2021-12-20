package com.example.fpt_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fpt_app.Fragment.ProductFragment;
import com.example.fpt_app.Models.AccessToken;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.Person;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    private Context context;
    TextView forgot;
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
        forgot = findViewById(R.id.forgotPass);
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

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, FogotActivity.class);
                startActivity(i);
            }
        });
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
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LayoutInflater layoutInflater = getLayoutInflater();

                    View layout = layoutInflater.inflate(R.layout.custom_toast_login, (ViewGroup)findViewById(R.id.toast));

                    final Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
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