package org.paramedic.homeless.currenciestest.service.data.repository;

import org.paramedic.homeless.currenciestest.service.data.Currency;

import java.math.BigDecimal;

/**
 * Created by codesanitar on 17/01/18.
 */

public class RateEntity {
    private int id;
    private String name;
    private BigDecimal value;
    private boolean base = false;
    private String date;
    private String description;
    private int imageId;

    RateEntity(String name) {
        final Currency currency = Currency.valueOf(name);
        this.id = currency.getId();
        this.name = currency.getName();
        this.description = currency.getDescription();
        this.imageId = currency.getImageId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
