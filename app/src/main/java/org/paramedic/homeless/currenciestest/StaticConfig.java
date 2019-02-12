package org.paramedic.homeless.currenciestest;

import java.math.RoundingMode;

/**
 * Created by codesanitar on 12/02/19.
 */

public final class StaticConfig {
    public static final long REQUEST_INTERVAL_SECONDS = 1;
    public static final long TIMEOUT_CONNECTION = 45;
    public static final long TIMEOUT_SOCKET = 30;
    public static final String BASE_URL = "https://revolut.duckdns.org/";
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
}
