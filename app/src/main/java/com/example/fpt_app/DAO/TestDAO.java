package com.example.fpt_app.DAO;


import com.example.fpt_app.API.APIManager;

public class TestDAO implements ITest{
    @Override
    public String get(String url) {
        APIManager apiManager = new APIManager();
        return apiManager.get(url);
    }
}
