package org.paramedic.homeless.currenciestest.service.api.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

import org.paramedic.homeless.currenciestest.service.api.NetworkUtils;
import org.paramedic.homeless.currenciestest.service.api.exception.NoConnectivityException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class ConnectivityInterceptor implements Interceptor {
 
    private final Context mContext;
 
    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }
 
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!NetworkUtils.isOnline(mContext)) {
            throw new NoConnectivityException();
        }
 
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
 
}