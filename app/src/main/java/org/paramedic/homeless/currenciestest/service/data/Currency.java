package org.paramedic.homeless.currenciestest.service.data;

import android.support.annotation.DrawableRes;

import org.paramedic.homeless.currenciestest.R;

/**
 * Created by codesanitar on 16/01/18.
 */

public enum Currency {
    AUD(1, "AUD", "Australian Dollar", R.drawable.aud),
    BGN(2, "BGN", "Bulgarian Lev", R.drawable.bgn),
    BRL(3, "BRL", "Brazilian Real", R.drawable.brl),
    CAD(4, "CAD", "Canadian Dollar", R.drawable.cad),
    CHF(5, "CHF", "Swiss Franc", R.drawable.chf),
    CNY(6, "CNY", "Chinese Yuan Renminbi", R.drawable.cny),
    CZK(7, "CZK", "Czech Koruna", R.drawable.czk),
    DKK(8, "DKK", "Danish Krone", R.drawable.dkk),
    GBP(9, "GBP", "British Pound", R.drawable.gbp),
    HKD(10, "HKD", "Hong Kong Dollar", R.drawable.hkd),
    HRK(11, "HRK", "Croatian Kuna", R.drawable.hrk),
    HUF(12, "HUF", "Hungarian Forint", R.drawable.huf),
    IDR(13, "IDR", "Indonesian Rupiah", R.drawable.idr),
    ILS(14, "ILS", "Israeli Shekel", R.drawable.ils),
    INR(15, "INR", "Indian Rupee", R.drawable.inr),
    JPY(16, "JPY", "Japanese Yen", R.drawable.jpy),
    KRW(17, "KRW", "South Korean Won", R.drawable.krw),
    MXN(18, "MXN", "Mexican Peso", R.drawable.mxn),
    MYR(19, "MYR", "Malaysian Ringgit", R.drawable.myr),
    NOK(20, "NOK", "Norwegian Krone", R.drawable.nok),
    NZD(21, "NZD", "New Zealand Dollar", R.drawable.nzd),
    PHP(22, "PHP", "Philippine Piso", R.drawable.php),
    PLN(23, "PLN", "Polish Zloty", R.drawable.pln),
    RON(24, "RON", "Romanian Leu", R.drawable.ron),
    RUB(25, "RUB", "Russian Ruble", R.drawable.rub),
    SEK(26, "SEK", "Swedish Krona", R.drawable.sek),
    SGD(27, "SGD", "Singapore Dollar", R.drawable.sgd),
    THB(28, "THB", "Thai Baht", R.drawable.thb),
    TRY(29, "TRY", "Turkish Lira", R.drawable.try_pic),
    USD(30, "USD", "US Dollar", R.drawable.usd),
    ZAR(31, "ZAR", "South African Rand", R.drawable.zar),
    EUR(32, "EUR", "Euro", R.drawable.eur);

    private final int id;
    private final String name;
    private final String description;
    private final int imageId;

    Currency(int id, String name, String description, @DrawableRes int imageId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageId() {
        return imageId;
    }
}
