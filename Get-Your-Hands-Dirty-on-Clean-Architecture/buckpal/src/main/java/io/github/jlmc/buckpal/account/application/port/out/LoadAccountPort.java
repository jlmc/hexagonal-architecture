package io.github.jlmc.buckpal.account.application.port.out;

import io.github.jlmc.buckpal.account.domain.Account;
import io.github.jlmc.buckpal.account.domain.AccountId;

import java.time.Instant;

public interface LoadAccountPort {

    Account loadAccount(AccountId accountId, Instant baselineDate);
}
