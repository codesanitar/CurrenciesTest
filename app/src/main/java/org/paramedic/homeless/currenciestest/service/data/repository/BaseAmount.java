package org.paramedic.homeless.currenciestest.service.data.repository;

import java.math.BigDecimal;

/**
 * Created by codesanitar on 18/01/18.
 */

public class BaseAmount {
    private int id;
    private BigDecimal value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
