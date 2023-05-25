package io.github.jlmc.buckpal.account.application.port.out;

import io.github.jlmc.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);
}
