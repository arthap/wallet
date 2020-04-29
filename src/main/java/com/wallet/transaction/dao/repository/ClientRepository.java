package com.wallet.transaction.dao.repository;

import com.wallet.transaction.dao.domain.ClientEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

}
