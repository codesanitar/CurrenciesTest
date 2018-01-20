package org.paramedic.homeless.currenciestest.service.data.repository;

/**
 * Created by codesanitar on 17/01/18.
 */

public class ConvertibleRateEntity extends RateEntity{

    private String amount;

    ConvertibleRateEntity(RateEntity rateEntity) {
        super(rateEntity.getName());
        setBase(rateEntity.isBase());
        setDate(rateEntity.getDate());
        setValue(rateEntity.getValue());
    }

    public String getAmount() {
        return amount;
    }

    void setAmount(String amount) {
        this.amount = amount;
    }
}
