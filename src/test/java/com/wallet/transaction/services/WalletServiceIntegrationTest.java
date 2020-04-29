package com.wallet.transaction.services;

import com.wallet.transaction.dao.domain.ClientEntity;
import com.wallet.transaction.dao.domain.WalletEntity;
import com.wallet.transaction.dao.repository.ClientRepository;
import com.wallet.transaction.dao.repository.WalletRepository;
import com.wallet.transaction.exception.InsufficientBalanceException;
import com.wallet.transaction.mapper.ClientModelMapper;
import com.wallet.transaction.mapper.WalletModelMapper;
import com.wallet.transaction.model.WalletDto;
import com.wallet.transaction.repository.ClientRepoTest;
import com.wallet.transaction.repository.WalletRepoTest;
import com.wallet.transaction.services.impl.ClientServiceImpl;
import com.wallet.transaction.services.impl.WalletServiceImpl;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WalletServiceIntegrationTest {

    @TestConfiguration
    static class WalletServiceTestContextConfiguration {

        @Bean
        public ClientService clientService() {
            return new ClientServiceImpl();
        }

        @Bean
        public WalletModelMapper walletModelMapper() {
            return new WalletModelMapper();
        }

        @Bean
        public ClientModelMapper clientModelMapper() {
            return new ClientModelMapper();
        }
    }

    @InjectMocks
    private WalletServiceImpl walletService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Resource
    private WalletModelMapper walletModelMapper;

    @Autowired
    private ClientModelMapper clientModelMapper;

    @Before
    public void setup() {
        clientRepository.deleteAll();
        walletRepository.deleteAll();
        ReflectionTestUtils.setField(walletService, "walletModelMapper", walletModelMapper);
        ReflectionTestUtils.setField(walletService, "walletRepository", walletRepository);
        ReflectionTestUtils.setField(walletService, "clientModelMapper", clientModelMapper);
    }

    @Test
    public void wallet_change_balance_success() {


        ClientEntity client = clientRepository.save(ClientRepoTest.getClientEntity());
        WalletEntity wallet = WalletRepoTest.getWalletEntity();
        BigDecimal bigDecimal = new BigDecimal(10000);
        wallet.setBalance(bigDecimal);
        wallet.setClient(client);

        WalletEntity walletEntity = walletRepository.save(wallet);

        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                walletService.withdrawalBalance(walletEntity.getId(), new BigDecimal(1000));
                walletService.replenishBalance(walletEntity.getId(), new BigDecimal(1000));
            });
        }

        WalletDto result = walletService.getById(walletEntity.getId());

        Assert.assertEquals(bigDecimal, result.getBalance());

    }

    @Test(expected = InsufficientBalanceException.class)
    public void wallet_change_balance_with_InsufficientBalanceException() {

        ClientEntity client = clientRepository.save(ClientRepoTest.getClientEntity());
        WalletEntity wallet = WalletRepoTest.getWalletEntity();
        BigDecimal bigDecimal = new BigDecimal(10);
        wallet.setBalance(bigDecimal);
        wallet.setClient(client);

        WalletEntity walletEntity = walletRepository.save(wallet);

        walletService.withdrawalBalance(walletEntity.getId(), new BigDecimal(10000000));


    }
}
