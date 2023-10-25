package com.bioproj.service;

import com.bioproj.pojo.Repos;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;

public interface GiteaHttpService {

    @GET("/api/v1/orgs/{org}/repos")
    Call<List<Repos>> getRepos(@Path("org") String org);



}
