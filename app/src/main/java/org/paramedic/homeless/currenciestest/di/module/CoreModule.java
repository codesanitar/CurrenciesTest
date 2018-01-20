package org.paramedic.homeless.currenciestest.di.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.paramedic.homeless.currenciestest.StaticConfig;
import org.paramedic.homeless.currenciestest.service.api.DuckDNSClient;
import org.paramedic.homeless.currenciestest.service.api.interceptor.ConnectivityInterceptor;
import org.paramedic.homeless.currenciestest.service.data.repository.RatesRepositoryImpl;
import org.paramedic.homeless.currenciestest.service.data.repository.RatesRepository;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by codesanitar on 16/01/18.
 */

@Module
public class CoreModule {

    private final Application app;

    public CoreModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(StaticConfig.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(StaticConfig.TIMEOUT_SOCKET, TimeUnit.SECONDS)
                .writeTimeout(StaticConfig.TIMEOUT_SOCKET, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(logging);
        okHttpClientBuilder.addInterceptor(new ConnectivityInterceptor(app));

        File cacheDir = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        int cacheSize = 10 * 1024 * 1024;
        try {
            Cache cache = new Cache(cacheDir, cacheSize);
            okHttpClientBuilder.cache(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return okHttpClientBuilder.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(StaticConfig.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    DuckDNSClient duckDNSClient(Retrofit retrofit) {
        return retrofit.create(DuckDNSClient.class);
    }

    @Provides
    @Singleton
    RatesRepository getCurrenciesContent() {
        return new RatesRepositoryImpl();
    }
}
