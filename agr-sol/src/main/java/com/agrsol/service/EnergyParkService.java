package com.agrsol.service;

import com.agrsol.model.EnergyParkEntity;
import com.agrsol.model.EnergyParkType;
import com.agrsol.repository.EnergyParkRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnergyParkService {

    private final EnergyParkRepository energyParkRepository;
/*
    - Create an offer
- Create a park
- List offers for each market
- List of parks on a single market*/

    public EnergyParkEntity createEnergyPark(EnergyParkEntity energyPark) {
        return this.energyParkRepository.save(energyPark);
    }

    public Page<EnergyParkEntity> getEnergyParksByType(EnergyParkType energyParkType,
                                                       Integer page,
                                                       Integer size,
                                                       String sort) {
        return this.energyParkRepository.findAllByType(energyParkType, PageRequest.of(page, size, Sort.by(sort)));
    }
}
