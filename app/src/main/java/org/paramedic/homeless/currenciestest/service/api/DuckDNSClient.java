package org.paramedic.homeless.currenciestest.service.api;

import org.paramedic.homeless.currenciestest.service.data.response.RateResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by codesanitar on 12/02/19.
 */

public interface DuckDNSClient {

    @GET("/latest")
    @Headers("Cache-control: no-cache")
    Single<RateResponse> latestRates(
            @Query("base") String base
    );
}
