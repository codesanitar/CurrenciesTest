package org.paramedic.homeless.currenciestest;

/**
 * Created by codesanitar on 12/02/19.
 */

public class StringUtils {
    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
