package io.github.jlmc.buckpal.account.adapter.out.persistence;

import io.github.jlmc.buckpal.account.domain.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AccountMapper {
    public Account mapToDomainEntity(AccountJpaEntity account, List<ActivityJpaEntity> activities, BigDecimal withdrawalBalance, BigDecimal depositBalance) {
        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance));

        AccountId accountId =
                Optional.ofNullable(account.getId())
                        .map(UUID::toString)
                        .map(AccountId::of)
                        .orElseThrow(IllegalStateException::new);

        return Account.withId(
                accountId,
                baselineBalance,
                mapToActivityWindow(activities));
    }

    private ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {
        List<Activity> mappedActivities = new ArrayList<>();

        for (ActivityJpaEntity activity : activities) {
            var activityId = new ActivityId(activity.getId());
            mappedActivities.add(
                    Activity.withId(
                            activityId,
                            AccountId.of(activity.getOwnerAccountId()),
                            AccountId.of(activity.getSourceAccountId()),
                            AccountId.of(activity.getTargetAccountId()),
                            activity.getTimestamp(),
                            Money.of(activity.getAmount())));
        }

        return new ActivityWindow(mappedActivities);
    }

    ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return new ActivityJpaEntity(
                activity.getId() == null ? null : activity.getId().value(),
                activity.getTimestamp(),
                activity.getOwnerAccountId().getValue(),
                activity.getSourceAccountId().getValue(),
                activity.getTargetAccountId().getValue(),
                activity.getMoney().getAmount());
    }
}
