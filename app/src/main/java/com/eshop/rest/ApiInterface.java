package com.eshop.rest;



import com.eshop.model.Accountlistresponse;
import com.eshop.model.BasicResponse;
import com.eshop.model.BasicSend;
import com.eshop.model.Orderlist;
import com.eshop.model.SendCheckOutResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<BasicResponse> login(@Field("email") String email, @Field("password") String password);

//
//
    @FormUrlEncoded
    @POST("register.php")
    Call<BasicResponse>register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password);


    @POST("es_catagories.php")
    Call<BasicResponse> loadFaq();



    @FormUrlEncoded
    @POST("es_product_list.php")
    Call<BasicResponse> loadCatagories(@Field("catagorey") String catagorey,
                                       @Field("subcatagorey") String subcatagorey);


    @FormUrlEncoded
    @POST("orderlist.php")
    Call<Orderlist> orderdetail(@Field("client") String client);


    @FormUrlEncoded
    @POST("getaccountdetail.php")
    Call<Accountlistresponse> getaccountdetail(@Field("id") int id);


    @FormUrlEncoded
    @POST("profileupdate.php")
    Call<BasicResponse> updateAccount(    @Field("name") String name,
                                          @Field("email") String email,
                                          @Field("phone") String phone,
                                          @Field("id") int id);


    @POST("checkout.php")
    Call<BasicResponse> updateSkills(@Body BasicSend task);




}