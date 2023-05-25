package io.github.jlmc.buckpal.account.domain;

import java.time.Instant;

public class Activity {

    /**
     * The account that owns this activity.
     */
    private final AccountId ownerAccountId;
    /**
     * The debited account.
     */
    private final AccountId sourceAccountId;
    /**
     * The credited account.
     */
    private final AccountId targetAccountId;
    /**
     * The timestamp of the activity.
     */
    private final Instant timestamp;
    /**
     * The money that was transferred between the accounts.
     */
    private final Money money;

    private ActivityId id;


    public Activity(AccountId ownerAccountId,
                     AccountId sourceAccountId,
                     AccountId targetAccountId,
                     Instant timestamp,
                     Money money) {
        this.id = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    public static Activity withId(ActivityId id,
                                  AccountId ownerAccountId,
                                  AccountId sourceAccountId,
                                  AccountId targetAccountId,
                                  Instant timestamp,
                                  Money money) {
        Activity activity = new Activity(ownerAccountId, sourceAccountId, targetAccountId, timestamp, money);
        activity.id = id;

        return activity;
    }


    public AccountId getOwnerAccountId() {
        return ownerAccountId;
    }

    public AccountId getSourceAccountId() {
        return sourceAccountId;
    }

    public AccountId getTargetAccountId() {
        return targetAccountId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Money getMoney() {
        return money;
    }

    public ActivityId getId() {
        return id;
    }//@formatter:off

}
