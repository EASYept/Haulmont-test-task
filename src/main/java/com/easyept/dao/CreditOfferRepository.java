package com.easyept.dao;

import com.easyept.entity.CreditOffer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditOfferRepository extends CrudRepository<CreditOffer, UUID> {

    List<CreditOffer> findAll();

}
