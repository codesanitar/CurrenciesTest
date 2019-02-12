package org.paramedic.homeless.currenciestest.service.data.repository;

import org.paramedic.homeless.currenciestest.service.data.Currency;

import java.math.BigDecimal;

/**
 * Created by codesanitar on 12/02/19.
 */

public class RateEntity {
    private BigDecimal value;
    private String amount;
    final private Currency currency;

    RateEntity(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
