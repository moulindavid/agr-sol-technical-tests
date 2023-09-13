package com.agrsol.controller;

import com.agrsol.model.EnergyParkEntity;
import com.agrsol.model.EnergyParkType;
import com.agrsol.service.EnergyParkService;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/energy-parks")
@RequiredArgsConstructor
public class EnergyParkController {

    private final EnergyParkService energyParkService;

    @PostMapping
    public ResponseEntity<EnergyParkEntity> createEnergyPark(@RequestBody EnergyParkEntity energyPark) {
        return new ResponseEntity<>(this.energyParkService.createEnergyPark(energyPark), HttpStatus.CREATED);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<Page<EnergyParkEntity>> createEnergyPark(
            @PathVariable EnergyParkType type,
            @Min(0) @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @Min(0) @Valid @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
            @Valid @RequestParam(value = "sort", required = false, defaultValue = "key") String sort) {
        return ResponseEntity.ok(this.energyParkService.getEnergyParksByType(type, page, size, sort));
    }
}
