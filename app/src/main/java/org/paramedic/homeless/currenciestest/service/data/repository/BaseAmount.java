package org.paramedic.homeless.currenciestest.service.data.repository;

import java.math.BigDecimal;

/**
 * Created by codesanitar on 12/02/19.
 */

public class BaseAmount {
    private final int id;
    private final BigDecimal value;

    public BaseAmount(int id, BigDecimal value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }
}
