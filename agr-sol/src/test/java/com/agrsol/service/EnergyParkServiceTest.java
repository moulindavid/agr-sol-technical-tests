package com.agrsol.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.agrsol.exception.OfferException;
import com.agrsol.model.EnergyParkEntity;
import com.agrsol.model.EnergyParkType;
import com.agrsol.repository.EnergyParkRepository;

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
class EnergyParkServiceTest {

    @SpyBean
    private EnergyParkRepository energyParkRepository;

    @Autowired
    private EnergyParkService energyParkService;

    @Test
    @Order(1)
    void createEnergyPark_correctEnergyPark_shouldCreateEnergyPark() {
        //GIVEN
        EnergyParkEntity energyParkToSave = EnergyParkEntity.builder()
                .type(EnergyParkType.WIND)
                .name("Wind park 🌪")
                .key("WP12")
                .description("Description")
                .build();

        //WHEN
        EnergyParkEntity savedEnergyPark = this.energyParkService.createEnergyPark(energyParkToSave);

        //THEN
        assertThat(savedEnergyPark).isNotNull();
        assertThat(savedEnergyPark.getId()).isNotNull();
        assertThat(savedEnergyPark.getType()).isEqualTo(EnergyParkType.WIND);
        assertThat(savedEnergyPark.getName()).isEqualTo("Wind park 🌪");
        assertThat(savedEnergyPark.getKey()).isEqualTo("WP12");
        assertThat(savedEnergyPark.getDescription()).isEqualTo("Description");
        verify(energyParkRepository).save(any());
    }

    @Test
    @Order(2)
    void getEnergyParksByMarket_shouldRetrieveEnergyParks() {
        //WHEN
        Page<EnergyParkEntity> pagedEnergyParks = this.energyParkService.getEnergyParksByType(EnergyParkType.WIND, 0, 20, "key");

        //THEN
        assertThat(pagedEnergyParks.getTotalElements()).isEqualTo(1);
    }
}