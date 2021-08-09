package com.easyept.service;

import com.easyept.dao.CreditOfferRepository;
import com.easyept.entity.CreditOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditOfferService {

    private final CreditOfferRepository creditOfferRepository;

    public CreditOfferService(@Autowired CreditOfferRepository creditOfferRepository) {
        this.creditOfferRepository = creditOfferRepository;
    }

    public CreditOffer save(CreditOffer creditOffer) {
        return creditOfferRepository.save(creditOffer);
    }

    public List<CreditOffer> findAll() {
        return creditOfferRepository.findAll();
    }

    public CreditOffer update(CreditOffer creditOffer) {
        return creditOfferRepository.save(creditOffer);
    }

    public void delete(CreditOffer creditOffer) {
        creditOfferRepository.delete(creditOffer);
    }
}
