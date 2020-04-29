package com.wallet.transaction.repository;//package com.wallet.transaction.repository;

import com.wallet.transaction.dao.domain.ClientEntity;
import com.wallet.transaction.dao.domain.WalletEntity;
import com.wallet.transaction.dao.repository.ClientRepository;
import com.wallet.transaction.dao.repository.WalletRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WalletRepoTest {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void wallet_save_success() {
        WalletEntity walletEntity = WalletRepoTest.getWalletEntity();

        ClientEntity clientEntity = ClientRepoTest.getClientEntity();

        ClientEntity savedClient = clientRepository.save(clientEntity);
        walletEntity.setClient(clientEntity);

        WalletEntity result = walletRepository.save(walletEntity);

        Assert.assertEquals(savedClient.getId(), result.getClient().getId());
        Assert.assertEquals("Gold", result.getName());
        Assert.assertEquals(bg, result.getBalance());
    }

    static BigDecimal bg = new BigDecimal(234567);

    static public WalletEntity getWalletEntity() {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(UUID.randomUUID());
        walletEntity.setBalance(bg);
        walletEntity.setName("Gold");
        return walletEntity;
    }
}
