package io.github.jlmc.buckpal.account.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Nested
    @DisplayName("When create a money instance")
    class CreateMoneyInstanceTests {

        @Test
        void withFineValue() {
            Money money = Money.of(BigDecimal.ONE);

            Assertions.assertNotNull(money);
        }

        @Test
        void withNullValue() {
            NullPointerException nullPointerException =
                    assertThrows(NullPointerException.class, () -> Money.of(null));
        }
    }

    @Nested
    class AddingMoneyTests {

    }

}
