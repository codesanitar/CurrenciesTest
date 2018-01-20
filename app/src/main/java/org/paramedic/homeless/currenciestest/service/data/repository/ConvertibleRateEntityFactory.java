package org.paramedic.homeless.currenciestest.service.data.repository;

@FunctionalInterface
interface ConvertibleRateEntityFactory<P extends ConvertibleRateEntity> {
    P create(RateEntity entity);
}