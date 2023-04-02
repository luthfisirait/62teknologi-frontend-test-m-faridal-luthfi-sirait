package com.example.mytest.api;


import com.example.mytest.dataApi.ResponseApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceDjango {


    @GET("businesses")
    Call<ResponseApi> homeresponse(
            @Query("pencarian") String pencarian
    );

}
