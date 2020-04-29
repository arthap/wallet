package com.wallet.transaction.dao.repository;

import com.wallet.transaction.dao.domain.BalanceTransactionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceHistoryTansactionRepository extends JpaRepository<BalanceTransactionEntity, UUID> {
}
