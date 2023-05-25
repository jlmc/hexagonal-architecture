package io.github.jlmc.buckpal.account.adapter.out.persistence;

import io.github.jlmc.buckpal.account.application.port.out.LoadAccountPort;
import io.github.jlmc.buckpal.account.application.port.out.UpdateAccountStatePort;
import io.github.jlmc.buckpal.account.domain.Account;
import io.github.jlmc.buckpal.account.domain.AccountId;
import io.github.jlmc.buckpal.account.domain.Activity;
import io.github.jlmc.buckpal.common.PersistenceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account loadAccount(AccountId accountId, Instant baselineDate) {
        AccountJpaEntity account =
                accountRepository.findById(UUID.fromString(accountId.getValue()))
                        .orElseThrow(EntityNotFoundException::new);

        List<ActivityJpaEntity> activities =
                activityRepository.findByOwnerSince(
                        accountId.getValue(),
                        baselineDate);

        BigDecimal withdrawalBalance = orZero(activityRepository
                .getWithdrawalBalanceUntil(
                        accountId.getValue(),
                        baselineDate));

        BigDecimal depositBalance = orZero(activityRepository
                .getDepositBalanceUntil(
                        accountId.getValue(),
                        baselineDate));

        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance
        );
    }

    private BigDecimal orZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    @Override
    public void updateActivities(Account account) {
        for (Activity activity : account.getActivityWindow().getActivities()) {
            if (activity.getId() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }

}
