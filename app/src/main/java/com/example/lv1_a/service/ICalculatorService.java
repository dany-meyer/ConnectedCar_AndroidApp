package com.example.lv1_a.service;

import com.example.lv1_a.model.CalculatorResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICalculatorService {

        @GET("/add")
        Call<CalculatorResponse> add(@Query("parameter1") int parameter1, @Query("parameter2") int parameter2);

        @GET("/substract")
        Call<CalculatorResponse> substract(@Query("parameter1") int parameter1, @Query("parameter2") int parameter2);

        @GET("/multiply")
        Call<CalculatorResponse> multiply(@Query("parameter1") int parameter1, @Query("parameter2") int parameter2);

        @GET("/divide")
        Call<CalculatorResponse> divide(@Query("parameter1") int parameter1, @Query("parameter2") int parameter2);


}
