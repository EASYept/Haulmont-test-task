package com.easyept.service;

import com.easyept.dao.BankRepository;
import com.easyept.entity.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(@Autowired BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    public Bank update(Bank bank) {
        return bankRepository.save(bank);
    }

    public void delete(Bank bank) {
        bankRepository.delete(bank);
    }
}
