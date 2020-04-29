package com.wallet.transaction.services;

import com.wallet.transaction.exception.InsufficientBalanceException;
import com.wallet.transaction.model.BalanceTransactionDto;
import com.wallet.transaction.model.TransactionStateType;
import com.wallet.transaction.model.TransactionType;
import com.wallet.transaction.model.WalletDto;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class TransactionConsumeProduceHandlerServiceTest {

    @InjectMocks
    TransactionConsumeProduceHandlerService transactionConsumeProduceHandlerService;

    @MockBean
    private WalletService walletService;

    @MockBean
    private BalanceHistoryService balanceHistoryService;

    @MockBean
    private TransactionSenderService transactionSenderService;
    private BigDecimal ecxpectedBalance;
    private UUID walletId;
    private UUID clientId;
    private UUID balanceId;
    private WalletDto ecxpectedWallet;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(transactionConsumeProduceHandlerService, "walletService", walletService);
        ReflectionTestUtils.setField(transactionConsumeProduceHandlerService, "balanceHistoryService", balanceHistoryService);
        ReflectionTestUtils.setField(transactionConsumeProduceHandlerService, "transactionSenderService", transactionSenderService);
        walletId = UUID.randomUUID();
        clientId = UUID.randomUUID();
        balanceId = UUID.randomUUID();
        ecxpectedBalance = new BigDecimal(5000);
        ecxpectedWallet = new WalletDto(walletId, clientId, ecxpectedBalance, "testName");
    }

    @Test
    public void sendTransaction_withdrawal_success() {
        BalanceTransactionDto balanceTransactionDto = new BalanceTransactionDto(walletId, ecxpectedBalance, new BigDecimal(1000), "description", TransactionType.WITHDRAWAL);
        doNothing().when(transactionSenderService).sendTransaction(balanceTransactionDto);
        doNothing().when(transactionSenderService).sendWallet(any());
        when(walletService.withdrawalBalance(any(), any())).thenReturn(ecxpectedWallet);
        balanceTransactionDto.setId(balanceId);
        when(balanceHistoryService.save(balanceTransactionDto)).thenReturn(balanceTransactionDto);
        BalanceTransactionDto consume = transactionConsumeProduceHandlerService.consume(balanceTransactionDto);

        Assert.assertEquals(balanceId, consume.getId());
        Assert.assertEquals(TransactionStateType.ACCEPTED, consume.getState());

        verify(transactionSenderService, times(1)).sendWallet(any());
        verify(balanceHistoryService, times(1)).save(any());

    }

    @Test
    public void sendTransaction_replenish_success() {
        BalanceTransactionDto balanceTransactionDto = new BalanceTransactionDto(walletId, ecxpectedBalance, new BigDecimal(1000), "description", TransactionType.REPLENISH);
        doNothing().when(transactionSenderService).sendTransaction(balanceTransactionDto);
        doNothing().when(transactionSenderService).sendWallet(any());
        when(walletService.replenishBalance(any(), any())).thenReturn(ecxpectedWallet);
        balanceTransactionDto.setId(balanceId);
        when(balanceHistoryService.save(balanceTransactionDto)).thenReturn(balanceTransactionDto);
        BalanceTransactionDto consume = transactionConsumeProduceHandlerService.consume(balanceTransactionDto);

        Assert.assertEquals(balanceId, consume.getId());
        Assert.assertEquals(TransactionStateType.ACCEPTED, consume.getState());

        verify(transactionSenderService, times(1)).sendWallet(any());
        verify(balanceHistoryService, times(1)).save(any());

    }

    @Test
    public void sendTransaction_withdrawal_rejected_with_InsufficientBalanceException() {
        BalanceTransactionDto balanceTransactionDto = new BalanceTransactionDto(walletId, ecxpectedBalance, new BigDecimal(10000000), "description", TransactionType.WITHDRAWAL);
        doNothing().when(transactionSenderService).sendTransaction(balanceTransactionDto);
        doNothing().when(transactionSenderService).sendDlq(any());
        doThrow(new InsufficientBalanceException("Error occurred")).when(walletService).withdrawalBalance(any(),any());
        balanceTransactionDto.setId(balanceId);
        when(balanceHistoryService.save(balanceTransactionDto)).thenReturn(balanceTransactionDto);
        BalanceTransactionDto consume = transactionConsumeProduceHandlerService.consume(balanceTransactionDto);

        Assert.assertEquals(balanceId, consume.getId());
        Assert.assertEquals(TransactionStateType.REJECTED, consume.getState());

        verify(transactionSenderService, times(1)).sendDlq(any());
        verify(balanceHistoryService, times(1)).save(any());
    }
}
