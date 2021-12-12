package com.example.fpt_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fpt_app.Adapter.OderCTAdapter;
import com.example.fpt_app.Models.AccessTokenManager;
import com.example.fpt_app.Models.OderCT;
import com.example.fpt_app.MyRetrofit.IRetrofitService;
import com.example.fpt_app.MyRetrofit.RetrofitBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongkeActivity extends AppCompatActivity {
    private RecyclerView mRecycle;
    private List<OderCT> data = new ArrayList<>();
    private static String BASE_URL = "http://10.0.2.2:8081/";
    private AccessTokenManager tokenManager;
    private OderCTAdapter adapter;
    Button btnNgay1,btnNgay2,btnLoc;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);

        mRecycle = findViewById(R.id.rcv_Oder);
        imgBack = findViewById(R.id.back_icon);
        btnNgay1= findViewById(R.id.btnNgay1);
        btnNgay2=findViewById(R.id.btnNgay2);
        btnLoc = findViewById(R.id.btnLoc);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        mRecycle.setLayoutManager(gridLayoutManager);
        tokenManager = AccessTokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        IRetrofitService service = new RetrofitBuilder()
                .createService(IRetrofitService.class, BASE_URL);
        service.OderCTGETALL().enqueue(getALLCart);

        // ngay 1
        btnNgay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date today = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);

                final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                final int months = cal.get(Calendar.MONTH);
                final int years = cal.get(Calendar.YEAR);
                final Calendar calendar = Calendar.getInstance();
                int date = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThongkeActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(i,i1,i2);
                        btnNgay1.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },years,months,dayOfWeek);
                datePickerDialog.show();
            }
        });


        //ngay 2
        btnNgay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date today = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);

                final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                final int months = cal.get(Calendar.MONTH);
                final int years = cal.get(Calendar.YEAR);
                final Calendar calendar = Calendar.getInstance();
                int date = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThongkeActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(i,i1,i2);
                        btnNgay2.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },years,months,dayOfWeek);
                datePickerDialog.show();
            }
        });

//loc hoa don
            btnLoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date from = simpleDateFormat.parse(btnNgay1.getText().toString());
                        Date to = simpleDateFormat.parse(btnNgay2.getText().toString());
                        List<OderCT> filter = data.stream().filter(oderCT -> {
                            try {
                                Date date = simpleDateFormat.parse(oderCT.getDate());
                                return  date.equals(from) || date.equals(to) || (date.after(from) && date.before(to)) ;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }).collect(Collectors.toList());
                        mRecycle.setAdapter(new OderCTAdapter(getBaseContext(), filter));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThongkeActivity.this,MainActivity.class));
            }
        });
    }
    Callback<List<OderCT>> getALLCart = new Callback<List<OderCT>>() {
        @Override
        public void onResponse(Call<List<OderCT>> call, Response<List<OderCT>> response) {
            if (response.isSuccessful()){
                if (data.size() == 0){
                    data = response.body();
                    adapter = new OderCTAdapter(getBaseContext(),data);
                    mRecycle.setAdapter(adapter);

                } else {
                    data.clear();
                    data.addAll(response.body());
                    mRecycle.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Log.e(">>>>>getAllCart onResponse", response.message());
            }
        }
        @Override
        public void onFailure(Call<List<OderCT>> call, Throwable t) {
            Log.e(">>>>>getAllCart onFailure", t.getMessage());
        }
    };
}