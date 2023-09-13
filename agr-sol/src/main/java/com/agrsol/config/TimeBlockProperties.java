package com.agrsol.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("time.block")
public class TimeBlockProperties {

    private int duration;
    private int number;
}
