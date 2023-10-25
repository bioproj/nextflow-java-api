package com.bioproj.service.impl;

import com.bioproj.pojo.Repos;
import com.bioproj.service.GiteaHttpService;
import com.bioproj.service.IGiteaService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class GiteaServiceImpl implements IGiteaService {

    @Value("${giteaUrl}")
    String giteaUrl;


    public GiteaHttpService gitInstance(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
//                                .header(HttpHeaders.AUTHORIZATION,HttpHeaders.AUTHORIZATION_BEARER_X + "anXNFXA8ng0ri04DIAz99Vdd")
//                                .header(HttpHeaders.ZOTERO_API_VERSION,"3")
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })

                .build();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(giteaUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))

//                .client(okHttpClient)
                .build();

        GiteaHttpService giteaHttpService = retrofit.create(GiteaHttpService.class);
        return giteaHttpService;
    }
    @Override
    public List<Repos> listRepos(String org){
        try {
            GiteaHttpService giteaHttpService = gitInstance();
            Call<List<Repos>> serviceRepos = giteaHttpService.getRepos(org);
            List<Repos> repos = serviceRepos.execute().body();
            return repos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
