package org.paramedic.homeless.currenciestest.service.api;

import org.paramedic.homeless.currenciestest.service.data.response.RateResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by codesanitar on 16/01/18.
 */

public interface DuckDNSClient {

    @GET("/latest")
    @Headers("Cache-control: no-cache")
    Flowable<RateResponse> latestRates(
            @Query("base") String base
    );
}
