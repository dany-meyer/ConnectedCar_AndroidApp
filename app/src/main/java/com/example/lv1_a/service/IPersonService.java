package com.example.lv1_a.service;
import com.example.lv1_a.model.Person;
import com.example.lv1_a.model.PersonServiceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IPersonService {
        @GET("/persons")
        Call<List<Person>> getPersons();
        @GET("/persons/{id}")
        Call<Person> getPerson(@Path("id") int id);
        @POST("/persons")
        Call<PersonServiceResponse> createPerson(@Body Person person);
        @PUT("/persons")
        Call<PersonServiceResponse> updatePerson(@Body Person person);
        @DELETE("/persons/{id}")
        Call<PersonServiceResponse> deletePerson(@Path("id") int id);
}
