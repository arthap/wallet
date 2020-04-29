package com.wallet.transaction.services;

import com.wallet.transaction.model.BalanceTransactionDto;
import java.util.List;
import java.util.UUID;

public interface BalanceHistoryService {

    List<BalanceTransactionDto> gelAll();

    BalanceTransactionDto getById(UUID id) throws RuntimeException;

    BalanceTransactionDto save(BalanceTransactionDto balanceTransactionDto);
}
