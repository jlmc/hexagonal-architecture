package io.github.jlmc.buckpal;

import io.github.jlmc.buckpal.account.application.service.MoneyTransferProperties;
import io.github.jlmc.buckpal.account.domain.Money;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BuckPalConfigurationProperties.class)
public class BuckPalConfiguration {

    @Bean
    public MoneyTransferProperties moneyTransferProperties(BuckPalConfigurationProperties buckPalConfigurationProperties) {
        long transferThreshold = buckPalConfigurationProperties.getTransferThreshold();

        return new MoneyTransferProperties(
                Money.of(transferThreshold),
                buckPalConfigurationProperties.getBaselineNumberOfDays()
        );
    }
}
