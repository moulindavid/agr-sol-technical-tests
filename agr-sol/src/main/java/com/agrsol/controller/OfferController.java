package com.agrsol.controller;

import com.agrsol.model.Market;
import com.agrsol.model.OfferEntity;
import com.agrsol.service.OfferService;

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
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<OfferEntity> createOffer(@RequestBody @Valid OfferEntity offer) {
        return new ResponseEntity<>(this.offerService.createOffer(offer), HttpStatus.CREATED);
    }

    @GetMapping("/market/{market}")
    public ResponseEntity<Page<OfferEntity>> createOffer(
            @PathVariable Market market,
            @Min(0) @Valid @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @Min(0) @Valid @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
            @Valid @RequestParam(value = "sort", required = false, defaultValue = "price") String sort) {
        return ResponseEntity.ok(this.offerService.getOffersByMarket(market, page, size, sort));
    }
}
