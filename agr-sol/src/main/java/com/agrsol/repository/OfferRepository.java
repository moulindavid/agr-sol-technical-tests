package com.agrsol.repository;

import com.agrsol.model.Market;
import com.agrsol.model.OfferEntity;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfferRepository extends JpaRepository<OfferEntity, UUID> {

    Page<OfferEntity> findAllByMarket(Market market, Pageable pageable);
}
