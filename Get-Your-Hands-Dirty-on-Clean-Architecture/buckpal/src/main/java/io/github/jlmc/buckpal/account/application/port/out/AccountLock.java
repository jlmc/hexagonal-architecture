package io.github.jlmc.buckpal.account.application.port.out;

import io.github.jlmc.buckpal.account.domain.AccountId;

public interface AccountLock {

    void lockAccount(AccountId accountId);

    void releaseAccount(AccountId accountId);
}
