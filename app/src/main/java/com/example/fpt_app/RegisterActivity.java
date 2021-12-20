package com.example.fpt_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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

import java.time.format.TextStyle;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTEN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //Ít nhất một chữ số
                    "(?=.*[a-z])" +         //Ít nhất một chữ thường
                    //"(?=.*[a-zA-Z])" +      //Any letter
                    "(?=.*[A-Z])" +         //Ít nhất một chữ hoa
                    "(?=.*[@#$%^&*=])" +    //Ít nhất một kí tự đặc biệt
                    "(?=\\S+$)" +           //Không được có khoảng trắng
                    ".{6,}" +               //Có ít nhất 6 kí tự
                    "$");
    private static final Pattern PHONE_PATTEN =
            Pattern.compile("^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$");
    private EditText fullname,edtEmail, edtPassword, edtConfirmPassword, phones;
    private Button btnRegister;
    int position;
    String email, roles;
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
        phones = findViewById(R.id.phone);
        roles = "1";

        setTitle("Register an account");


        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full_name = fullname.getText().toString();
                String newEmail = edtEmail.getText().toString();
                String newPassword = edtPassword.getText().toString();
                String confirmNewPassword = edtConfirmPassword.getText().toString();
                String phone = phones.getText().toString();

                if (TextUtils.isEmpty(full_name)){
                    fullname.setError("Tên không được để trống");
                    return;
                }else if (TextUtils.isEmpty(newEmail)){
                    edtEmail.setError("Email không được để trống");
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()){
                    edtEmail.setError("Email không đúng định dạng");
                    return;
                }else if (TextUtils.isEmpty(newPassword)){
                    edtPassword.setError("Mật khẩu không được để trống");
                    return;
                }else if (!PASSWORD_PATTEN.matcher(newPassword).matches()){
                    edtPassword.setError("Mật khẩu phải có: \n" +
                            "- Ít nhất một chữ số \n" +
                            "- Ít nhất một chữ thường \n" +
                            "- Ít nhất một chữ hoa \n" +
                            "- Ít nhất một kí tự đặc biệt \n" +
                            "- Không được có khoảng trắng \n" +
                            "- Ít nhất 6 kí tự");
                    return;
                }else if (newPassword.equals(confirmNewPassword) == false){
                    edtConfirmPassword.setError("Mật khẩu xác nhận không đúng");
                    return;
                }else if (TextUtils.isEmpty(phone)){
                    phones.setError("Số điện thoại không được để trống");
                    return;
                }else if (!PHONE_PATTEN.matcher(phone).matches()){
                    phones.setError("vui lòng nhập đúng định dạng số điện thoại");
                    return;
                } else{
                    service.dangky(new User(0,full_name, newEmail, newPassword, confirmNewPassword, phone, roles)).enqueue(registerCB);
                }


            }
        });
    }

    Callback<AccessToken> registerCB = new Callback<AccessToken>() {
        @Override
        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
            if (response.isSuccessful()) {
                AccessToken token = response.body();
                tokenManager.saveToken(token);
                Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Email đã tồn tại", Toast.LENGTH_SHORT).show();
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


