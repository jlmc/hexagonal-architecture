package io.github.jlmc.buckpal.account.domain;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A window of account activities.
 */
public class ActivityWindow {

    /**
     * The list of account activities within this window.
     */
    private final List<Activity> activities;

    public ActivityWindow(List<Activity> activities) {
        this.activities = activities;
    }


    /**
     * The timestamp of the first activity within this window.
     */
    public Instant getStartTimestamp() {
        return this.activities.stream()
                .min(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }

    /**
     * The timestamp of the last activity within this window.
     */
    public Instant getEndTimestamp() {
        return this.activities.stream()
                .max(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }


    /**
     * Calculates the balance by summing up the values of all activities within this window.
     */
    public Money calculateBalance(AccountId accountId) {
        var depositBalance =
                activities.stream()
                        .filter(a -> a.getTargetAccountId().equals(accountId))
                        .map(Activity::getMoney)
                        .reduce(Money.ZERO, Money::add);

        var withdrawalBalance =
                activities.stream()
                        .filter(a -> a.getSourceAccountId().equals(accountId))
                        .map(Activity::getMoney)
                        .reduce(Money.ZERO, Money::add);

        return Money.add(depositBalance, withdrawalBalance.negate());
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }
}
