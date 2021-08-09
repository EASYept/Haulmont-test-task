package com.easyept.dao;

import com.easyept.entity.Credit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditRepository extends CrudRepository<Credit, UUID> {

    List<Credit> findAll();

    @Query(
            value = "SELECT * FROM Credit credit WHERE bank_uuid IS null OR bank_uuid = :uuid",
            nativeQuery = true)
    List<Credit> findByBankUuid(UUID uuid);
}
