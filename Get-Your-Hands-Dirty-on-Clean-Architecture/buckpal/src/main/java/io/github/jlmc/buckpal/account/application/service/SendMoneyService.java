package io.github.jlmc.buckpal.account.application.service;

import io.github.jlmc.buckpal.account.application.port.in.SendMoneyCommand;
import io.github.jlmc.buckpal.account.application.port.in.SendMoneyUseCase;
import io.github.jlmc.buckpal.account.application.port.out.AccountLock;
import io.github.jlmc.buckpal.account.application.port.out.LoadAccountPort;
import io.github.jlmc.buckpal.account.application.port.out.UpdateAccountStatePort;
import io.github.jlmc.buckpal.account.domain.Account;
import io.github.jlmc.buckpal.account.domain.AccountId;
import io.github.jlmc.buckpal.common.UseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@UseCase
@Transactional
class SendMoneyService implements SendMoneyUseCase {

    private final MoneyTransferProperties moneyTransferProperties;

    private final LoadAccountPort loadAccountPort;

    private final UpdateAccountStatePort updateAccountStatePort;

    private final AccountLock accountLock;

    SendMoneyService(
            MoneyTransferProperties moneyTransferProperties,
            LoadAccountPort loadAccountPort,
            UpdateAccountStatePort updateAccountStatePort,
            AccountLock accountLock) {
        this.moneyTransferProperties = moneyTransferProperties;
        this.loadAccountPort = loadAccountPort;
        this.updateAccountStatePort = updateAccountStatePort;
        this.accountLock = accountLock;
    }


    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        checkThreshold(command);

        var baselineDate =
                ZonedDateTime.now()
                        .minusDays(moneyTransferProperties.getBaseLineNumberOfDays())
                        .toInstant();


        Account sourceAccount = loadAccountPort.loadAccount(
                command.getSourceAccountId(),
                baselineDate);

        Account targetAccount = loadAccountPort.loadAccount(
                command.getTargetAccountId(),
                baselineDate);

        AccountId sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));

        AccountId targetAccountId = targetAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));


        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);
        return true;
    }

    private void checkThreshold(SendMoneyCommand command) {
        if (command.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
            throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.getMoney());
        }
    }
}
