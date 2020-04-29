package com.wallet.transaction.repository;

import com.wallet.transaction.dao.domain.ClientEntity;
import com.wallet.transaction.dao.domain.WalletEntity;
import com.wallet.transaction.dao.repository.ClientRepository;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepoTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void client_save_success() {
        WalletEntity walletEntity = WalletRepoTest.getWalletEntity();

        ClientEntity clientEntity = getClientEntity();

        clientEntity.setWalletList(Arrays.asList(walletEntity));
        ClientEntity savedClient = clientRepository.save(clientEntity);
        ClientEntity inDb = clientRepository.getOne(savedClient.getId());

        WalletEntity wallet = clientEntity.getWalletList().get(0);


        Assert.assertEquals(inDb.getId(), savedClient.getId());
        Assert.assertEquals("testEmail", savedClient.getEmail());
        Assert.assertEquals("testLastName", savedClient.getLastName());
        Assert.assertEquals("Gold", wallet.getName());
        Assert.assertEquals(WalletRepoTest.bg, wallet.getBalance());
    }

    static public ClientEntity getClientEntity() {
        return new ClientEntity("testName", "testLastName", "testEmail", "testPhone");
    }

}
