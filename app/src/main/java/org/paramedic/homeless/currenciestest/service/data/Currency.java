package org.paramedic.homeless.currenciestest.service.data;

import android.support.annotation.DrawableRes;

import org.paramedic.homeless.currenciestest.R;

/**
 * Created by codesanitar on 12/02/19.
 */

public enum Currency {
    AUD(1,  "Australian Dollar", R.drawable.aud),
    BGN(2,  "Bulgarian Lev", R.drawable.bgn),
    BRL(3,  "Brazilian Real", R.drawable.brl),
    CAD(4,  "Canadian Dollar", R.drawable.cad),
    CHF(5,  "Swiss Franc", R.drawable.chf),
    CNY(6,  "Chinese Yuan Renminbi", R.drawable.cny),
    CZK(7,  "Czech Koruna", R.drawable.czk),
    DKK(8,  "Danish Krone", R.drawable.dkk),
    GBP(9,  "British Pound", R.drawable.gbp),
    HKD(10, "Hong Kong Dollar", R.drawable.hkd),
    HRK(11, "Croatian Kuna", R.drawable.hrk),
    HUF(12, "Hungarian Forint", R.drawable.huf),
    IDR(13, "Indonesian Rupiah", R.drawable.idr),
    ILS(14, "Israeli Shekel", R.drawable.ils),
    INR(15, "Indian Rupee", R.drawable.inr),
    JPY(16, "Japanese Yen", R.drawable.jpy),
    KRW(17, "South Korean Won", R.drawable.krw),
    MXN(18, "Mexican Peso", R.drawable.mxn),
    MYR(19, "Malaysian Ringgit", R.drawable.myr),
    NOK(20, "Norwegian Krone", R.drawable.nok),
    NZD(21, "New Zealand Dollar", R.drawable.nzd),
    PHP(22, "Philippine Piso", R.drawable.php),
    PLN(23, "Polish Zloty", R.drawable.pln),
    RON(24, "Romanian Leu", R.drawable.ron),
    RUB(25, "Russian Ruble", R.drawable.rub),
    SEK(26, "Swedish Krona", R.drawable.sek),
    SGD(27, "Singapore Dollar", R.drawable.sgd),
    THB(28, "Thai Baht", R.drawable.thb),
    TRY(29, "Turkish Lira", R.drawable.try_pic),
    USD(30, "US Dollar", R.drawable.usd),
    ZAR(31, "South African Rand", R.drawable.zar),
    EUR(32, "Euro", R.drawable.eur),
    ISK(33, "Iceland Krona", R.drawable.isk);

    private final int id;
    private final String description;
    private final int imageId;

    Currency(int id, String description, @DrawableRes int imageId) {
        this.id = id;
        this.description = description;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getImageId() {
        return imageId;
    }

    public static boolean isValid(String value)
    {
        for(Currency currency:values())
            if (currency.name().equals(value))
                return true;
        return false;
    }
}
