package org.paramedic.homeless.currenciestest.service.data.response;

import com.google.gson.JsonObject;

/**
 * Created by codesanitar on 12/02/19.
 */

public class RateResponse {
    private String base;
    private String date;
    private JsonObject rates;

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public JsonObject getRates() {
        return rates;
    }
}
