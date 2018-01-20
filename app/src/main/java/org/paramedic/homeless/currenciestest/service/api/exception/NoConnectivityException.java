package org.paramedic.homeless.currenciestest.service.api.exception;

import java.io.IOException;

public class NoConnectivityException extends IOException {
 
    @Override
    public String getMessage() {
        return "No connectivity exception";
    }
 
}