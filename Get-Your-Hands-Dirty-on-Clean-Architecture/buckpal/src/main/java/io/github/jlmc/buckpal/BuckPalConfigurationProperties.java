package io.github.jlmc.buckpal;

import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "buckpal")
public class BuckPalConfigurationProperties {

    @Positive
    private long transferThreshold = Long.MAX_VALUE;

    @Positive
    private long baselineNumberOfDays = 10L;

    public long getTransferThreshold() {
        return transferThreshold;
    }

    public void setTransferThreshold(long transferThreshold) {
        this.transferThreshold = transferThreshold;
    }

    public long getBaselineNumberOfDays() {
        return baselineNumberOfDays;
    }

    public void setBaselineNumberOfDays(long baselineNumberOfDays) {
        this.baselineNumberOfDays = baselineNumberOfDays;
    }
}
