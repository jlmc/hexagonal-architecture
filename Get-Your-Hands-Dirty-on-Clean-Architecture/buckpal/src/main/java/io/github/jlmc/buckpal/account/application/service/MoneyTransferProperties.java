package io.github.jlmc.buckpal.account.application.service;

import io.github.jlmc.buckpal.account.domain.Money;

public class MoneyTransferProperties {

    private Money maximumTransferThreshold = Money.of(1_000_000L);

    private final long baseLineNumberOfDays;

    public MoneyTransferProperties(Money maximumTransferThreshold, long baseLineNumberOfDays) {
        this.maximumTransferThreshold = maximumTransferThreshold;
        this.baseLineNumberOfDays = baseLineNumberOfDays;
    }

    public Money getMaximumTransferThreshold() {
        return maximumTransferThreshold;
    }

    public long getBaseLineNumberOfDays() {
        return baseLineNumberOfDays;
    }
}
