package com.agrsol.config;

import com.agrsol.model.Market;

import org.springframework.core.convert.converter.Converter;


public class MarketConverter implements Converter<String, Market> {
    @Override
    public Market convert(String source) {
        try {
            return Market.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
