package com.agrsol.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "offer")
public class OfferEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "binary(16)")
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Market market;
    private BigDecimal energy;
    private BigDecimal price;
    @Column(name = "block_date", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate blockDate;
    /**
     * if a day is divided in 6 blocks of 4 hours
     * 1 -> 6
     */
    @Column(name = "block_number", nullable = false)
    @Min(0)
    private Integer blockNumber;
    private UUID energyParkId;
}
