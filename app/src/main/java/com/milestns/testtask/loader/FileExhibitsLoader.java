package com.milestns.testtask.loader;

import android.annotation.SuppressLint;

import com.google.gson.GsonBuilder;
import com.milestns.testtask.model.ExhibitsLoader;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alan on 20.12.2017.
 */

public class FileExhibitsLoader {
    private static final String BASE_URL = "https://gist.githubusercontent.com/";
    private static final int TIMEOUT = 30000;

    private static FileExhibitsLoader instance;


    public static FileExhibitsLoader getInstance() {
        if (instance == null) {
            instance = new FileExhibitsLoader();
        }
        return instance;
    }

    private ExhibitsLoader exhibitsLoader;

    public FileExhibitsLoader() {

    }

    public ExhibitsLoader getExhibitsLoader() {
        if (exhibitsLoader == null) {
            exhibitsLoader = buildRetrofit().create(ExhibitsLoader.class);
        }
        return exhibitsLoader;
    }

    private Retrofit buildRetrofit() {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new MyInterceptor()).build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().disableHtmlEscaping().create()))
                .build();
    }

    class MyInterceptor implements Interceptor {
        @SuppressLint("DefaultLocale")
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %n%s , %s", request.url(), request.headers(), request.body()));

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().

                                    url(),
                            (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }
}
