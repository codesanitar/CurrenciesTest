package org.paramedic.homeless.currenciestest.service.data.repository;

@FunctionalInterface
interface RateEntityFactory<P extends RateEntity> {
    P create(String currency);
}