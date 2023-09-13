package com.agrsol.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.agrsol.config.TimeBlockProperties;
import com.agrsol.exception.OfferException;
import com.agrsol.model.EnergyParkEntity;
import com.agrsol.model.EnergyParkType;
import com.agrsol.model.Market;
import com.agrsol.model.OfferEntity;
import com.agrsol.repository.EnergyParkRepository;
import com.agrsol.repository.OfferRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OfferServiceTest {

    @SpyBean
    private OfferRepository offerRepository;
    @SpyBean
    private EnergyParkRepository energyParkRepository;

    @Autowired
    private TimeBlockProperties timeBlockProperties;


    @Autowired
    private OfferService offerService;

    private EnergyParkEntity savedEnergyPark;

    @BeforeAll
    void setup() {
        savedEnergyPark = energyParkRepository.save(EnergyParkEntity.builder().type(EnergyParkType.SOLAR).build());
    }

    @Test
    @Order(1)
    void createOffer_correctTimeBlock_shouldCreateOffer() {
        //GIVEN

        //WHEN
        OfferEntity savedOffer = this.offerService.createOffer(OfferEntity.builder()
                .blockDate(LocalDate.now())
                .blockNumber(1)
                .market(Market.PRIMARY)
                .price(BigDecimal.valueOf(12.1))
                .energy(BigDecimal.valueOf(122.1))
                .energyParkId(savedEnergyPark.getId())
                .build());
        this.offerService.createOffer(OfferEntity.builder()
                .blockDate(LocalDate.now())
                .blockNumber(3)
                .market(Market.PRIMARY)
                .price(BigDecimal.valueOf(12.1))
                .energy(BigDecimal.valueOf(122.1))
                .energyParkId(savedEnergyPark.getId())
                .build());
        //THEN
        assertThat(savedOffer).isNotNull();
        assertThat(savedOffer.getId()).isNotNull();
        assertThat(savedOffer.getBlockNumber()).isEqualTo(1);
        assertThat(savedOffer.getMarket()).isEqualTo(Market.PRIMARY);
        assertThat(savedOffer.getPrice()).isEqualTo(BigDecimal.valueOf(12.1));
        assertThat(savedOffer.getEnergy()).isEqualTo(BigDecimal.valueOf(122.1));
        assertThat(savedOffer.getEnergyParkId()).isEqualTo(savedEnergyPark.getId());
        verify(offerRepository, times(2)).save(any());
    }

    @Test
    @Order(2)
    void createOffer_incorrectTimeBlock_shouldFailAndThrowError() {
        //GIVEN
        OfferEntity offerToSave = OfferEntity.builder()
                .blockDate(LocalDate.now())
                .blockNumber(100)
                .market(Market.PRIMARY)
                .price(BigDecimal.valueOf(12.1))
                .energy(BigDecimal.valueOf(123.1))
                .energyParkId(savedEnergyPark.getId())
                .build();

        //WHEN
        assertEquals(String.format("The time block of the offer (100) is too big, the last block of the day is %s", timeBlockProperties.getNumber()), assertThrows(OfferException.class, () -> this.offerService.createOffer(offerToSave)).getMessage());
    }

    @Test
    @Order(3)
    void getOffersByMarket_shouldRetrieveOffers() {
        //WHEN
        Page<OfferEntity> pagedOffers = this.offerService.getOffersByMarket(Market.PRIMARY, 0, 20, "blockDate");

        //THEN
        assertThat(pagedOffers.getTotalElements()).isEqualTo(2);
    }
}