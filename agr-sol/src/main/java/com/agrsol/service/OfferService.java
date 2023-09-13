package com.agrsol.service;

import com.agrsol.config.TimeBlockProperties;
import com.agrsol.exception.OfferException;
import com.agrsol.model.Market;
import com.agrsol.model.OfferEntity;
import com.agrsol.repository.OfferRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final TimeBlockProperties timeBlockProperties;


    @Transactional
    public OfferEntity createOffer(OfferEntity offerEntity) {

        if (offerEntity.getBlockNumber() > timeBlockProperties.getNumber()) {
            throw new OfferException(String.format("The time block of the offer (%s) is too big, the last block of the day is %s", offerEntity.getBlockNumber(), timeBlockProperties.getNumber()));
        }

        return this.offerRepository.save(offerEntity);
    }

    public Page<OfferEntity> getOffersByMarket(Market market,
                                               Integer page,
                                               Integer size,
                                               String sort) {
        return this.offerRepository.findAllByMarket(market, PageRequest.of(page, size, Sort.by(sort)));
    }
}
