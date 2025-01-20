package com.veedev.api.coinsart.controllers;

import com.veedev.api.coinsart.service.CurrenciesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currencies")
@AllArgsConstructor
public class CurrenciesController {
    private final CurrenciesService currenciesService;

    @GetMapping("/all-for-one")
    public ResponseEntity<String> getAllForOne(@RequestParam String currency) {
        return currenciesService.getCurrencies(currency);
    }

    @GetMapping("/change-for-one")
    public ResponseEntity<String> getChangeForOne(@RequestParam String currency, @RequestParam String tradeCurrencies) {
        return currenciesService.getTradeCurrencies(currency, tradeCurrencies);
    }
}
