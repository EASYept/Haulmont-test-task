package com.easyept.service;

import com.easyept.dao.CreditRepository;
import com.easyept.entity.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CreditService {
    private final CreditRepository creditRepository;

    public CreditService(@Autowired CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public Credit save(Credit credit) {
        return creditRepository.save(credit);
    }

    public List<Credit> findAll() {
        return creditRepository.findAll();
    }

    public Credit update(Credit credit) {
        return creditRepository.save(credit);
    }

    public void delete(Credit credit) {
        creditRepository.delete(credit);
    }

    public List<Credit> findByBankUuid(UUID uuid) {
        return creditRepository.findByBankUuid(uuid);
    }
}
