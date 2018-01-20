package org.paramedic.homeless.currenciestest;

/**
 * Created by codesanitar on 18/01/18.
 */

public class StringUtils {
    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
