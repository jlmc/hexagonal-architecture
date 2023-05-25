package io.github.jlmc.buckpal.account.adapter.in.web;

import io.github.jlmc.buckpal.account.application.port.in.SendMoneyUseCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/accounts"})
public class AccountController {

    private final SendMoneyUseCase sendMoneyUseCase;

    public AccountController(SendMoneyUseCase sendMoneyUseCase) {
        this.sendMoneyUseCase = sendMoneyUseCase;
    }

    @PostMapping("/send/{sourceAccountId}/{targetAccountId}/{amount}")
    public void sendMoney(
            @PathVariable("sourceAccountId") String sourceAccountId,
            @PathVariable("targetAccountId") String targetAccountId,
            @PathVariable("amount") Long amount
            ) {

    }
}
