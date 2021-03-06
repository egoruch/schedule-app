package com.egor.pulse.app.services;

import com.egor.pulse.app.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Egor on 26.05.2016.
 */
public class ServiceGenerator {


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS);

    private static GameService gameService = null;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BuildConfig.REST_HOST)
                    .addConverterFactory(JacksonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static GameService getGameService() {
        if (gameService == null) {
            gameService = createService(GameService.class);
        }
        return gameService;
    }
}
