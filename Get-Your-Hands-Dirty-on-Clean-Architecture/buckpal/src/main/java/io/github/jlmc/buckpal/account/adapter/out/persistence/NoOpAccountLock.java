package io.github.jlmc.buckpal.account.adapter.out.persistence;

import io.github.jlmc.buckpal.account.application.port.out.AccountLock;
import io.github.jlmc.buckpal.account.domain.AccountId;
import org.springframework.stereotype.Component;

@Component
public class NoOpAccountLock implements AccountLock {
    @Override
    public void lockAccount(AccountId accountId) {

    }

    @Override
    public void releaseAccount(AccountId accountId) {

    }
}
