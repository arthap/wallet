package com.wallet.transaction.controller;

import com.wallet.transaction.services.TransactionExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ExecuteController {


    @Autowired
    TransactionExecuteService transactionExecuteService;

    @GetMapping("/execute")
    public void createAccount() {
        transactionExecuteService.run();
    }

}
