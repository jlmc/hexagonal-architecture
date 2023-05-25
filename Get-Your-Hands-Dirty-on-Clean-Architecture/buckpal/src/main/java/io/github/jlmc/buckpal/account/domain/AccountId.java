package io.github.jlmc.buckpal.account.domain;

import java.util.Objects;

public class AccountId {

    private final String value;

    private AccountId(String value) {
        this.value = value;
    }

    public static AccountId of(String value) {
        return new AccountId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId accountId)) return false;

        return Objects.equals(value, accountId.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
