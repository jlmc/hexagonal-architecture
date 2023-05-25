package io.github.jlmc.buckpal.account.application.port.in;

import io.github.jlmc.buckpal.account.domain.AccountId;
import io.github.jlmc.buckpal.account.domain.Money;
import io.github.jlmc.buckpal.common.SelfValidating;
import jakarta.validation.constraints.NotNull;

public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {
    @NotNull
    private final AccountId sourceAccountId;

    @NotNull
    private final AccountId targetAccountId;

    @NotNull
    private final Money money;

    public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }

    public AccountId getSourceAccountId() {
        return sourceAccountId;
    }

    public AccountId getTargetAccountId() {
        return targetAccountId;
    }

    public Money getMoney() {
        return money;
    }
}
