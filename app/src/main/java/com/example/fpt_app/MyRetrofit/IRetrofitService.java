package com.example.fpt_app.MyRetrofit;


import com.example.fpt_app.Models.AccessToken;
import com.example.fpt_app.Models.Cart;
import com.example.fpt_app.Models.Like;
import com.example.fpt_app.Models.Person;
import com.example.fpt_app.Models.Product;
import com.example.fpt_app.Models.ProductCategory;
import com.example.fpt_app.Models.Response2PikModel;
import com.example.fpt_app.Models.ResponseModel;
import com.example.fpt_app.Models.Shop;
import com.example.fpt_app.Models.TB;
import com.example.fpt_app.Models.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IRetrofitService {

    @GET("getOne.php")
    Call<Person> getOne();

    @GET("getArray.php")
    Call<List<Person>> getArray();

    @GET("getOneByParam.php")
    Call<Person> getOneByParam(@Query("id") int id);

    @POST("post.php")
    Call<Person> post();

    @POST("views/user_login.php")
    Call<AccessToken> login(@Body Person person);

    @POST("views/store_get_all.php")
    Call<List<Shop>> getAllShop();

    @POST("views/user_forgot_password.php")
    Call<AccessToken> chandpassword(@Body Person person);

    @POST("views/user_register.php")
    Call<AccessToken> dangky(@Body User user);

    @POST("views/user_get_profile.php")
    Call<User> Profile();

    @POST("views/product_get_all.php")
    Call<List<Product>> productGetAll();

    @POST("views/product_get_by_id.php")
    Call<Product> productGetById(@Body Product product);

    @POST("views/get_all_gh.php")
    Call<List<Cart>> CartGetALL();

    @POST("views/get_all_like.php")
    Call<List<Like>> LikeGetALL();

    @Multipart
    @POST("/")
    Call<Response2PikModel> upload(@Part MultipartBody.Part image);

    @POST("views/product_category_get_all.php")
    Call<List<ProductCategory>> productCategoryGetAll();

    @POST("views/product_insert.php")
    Call<ResponseModel> productInsert(@Body Product product);

    @POST("views/like_insert.php")
    Call<ResponseModel> LikeInsert(@Body Like like);

    @POST("views/gh_insert.php")
    Call<ResponseModel> CartInsert(@Body Cart cart);

    @POST("views/product_update.php")
    Call<ResponseModel> productUpdate(@Body Product product);

    @POST("views/product_delete.php")
    Call<ResponseModel> productDelete(@Body Product product);

    @POST("views/like_delete.php")
    Call<ResponseModel> likeDelete(@Body Like like);

    @POST("views/gh_delete.php")
    Call<ResponseModel> cart_delete(@Body Cart cart);

    @POST("views/get_all_TB.php")
    Call<List<TB>> TBgetALLL();

    @POST("views/store_insert.php")
    Call<ResponseModel> StoreInsert(@Body Shop shop);


}
