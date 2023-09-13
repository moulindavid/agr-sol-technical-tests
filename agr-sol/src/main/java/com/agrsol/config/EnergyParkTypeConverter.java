package com.agrsol.config;

import com.agrsol.model.EnergyParkType;

import org.springframework.core.convert.converter.Converter;


public class EnergyParkTypeConverter implements Converter<String, EnergyParkType> {
    @Override
    public EnergyParkType convert(String source) {
        try {
            return EnergyParkType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
