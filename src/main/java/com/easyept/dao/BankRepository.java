package com.easyept.dao;

import com.easyept.entity.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BankRepository extends CrudRepository<Bank, UUID> {

    List<Bank> findAll();

}
