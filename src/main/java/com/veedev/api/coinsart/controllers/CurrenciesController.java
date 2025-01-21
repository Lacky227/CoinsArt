package com.veedev.api.coinsart.controllers;

import com.veedev.api.coinsart.service.CurrenciesService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
@Validated
public class CurrenciesController {
    private final CurrenciesService currencyService;

    /**
     * Retrieves exchange rates for a specified base currency.
     *
     * @param baseCurrency The base currency code (e.g., USD).
     * @return Exchange rates for the base currency.
     */
    @GetMapping("/rates")
    public ResponseEntity<String> getExchangeRates(
            @RequestParam @NotBlank String baseCurrency) {
        return currencyService.fetchExchangeRates(baseCurrency);
    }

    /**
     * Retrieves exchange rates between a base currency and a list of target currencies.
     *
     * @param baseCurrency  The base currency code (e.g., USD).
     * @param targetCurrencies Comma-separated list of target currencies (e.g., EUR,GBP).
     * @return Exchange rates for the specified currencies.
     */
    @GetMapping("/rates/compare")
    public ResponseEntity<String> getExchangeRatesForCurrencies (
            @RequestParam @NotBlank String baseCurrency,
            @RequestParam @NotBlank String targetCurrencies) throws Exception {
        return currencyService.fetchExchangeRatesForCurrencies(baseCurrency, targetCurrencies);
    }
}
