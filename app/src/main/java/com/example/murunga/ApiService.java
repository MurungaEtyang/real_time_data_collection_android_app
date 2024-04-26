package com.example.murunga;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("submit_report_endpoint")
    Call<Void> submitReport(
            @Field("pestName") String pestName,
            @Field("diseaseCaused") String diseaseCaused,
            @Field("description") String description,
            @Field("selectedImages") String selectedImages
    );
}
