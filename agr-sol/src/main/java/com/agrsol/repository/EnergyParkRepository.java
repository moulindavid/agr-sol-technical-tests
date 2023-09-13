package com.agrsol.repository;

import com.agrsol.model.EnergyParkEntity;
import com.agrsol.model.EnergyParkType;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyParkRepository extends JpaRepository<EnergyParkEntity, UUID> {

    Page<EnergyParkEntity> findAllByType(EnergyParkType energyParkType, Pageable pageable);
}
