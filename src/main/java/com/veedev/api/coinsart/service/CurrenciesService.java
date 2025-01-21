package com.veedev.api.coinsart.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CurrenciesService {
    ResponseEntity<String> fetchExchangeRates(String baseCurrency);
    ResponseEntity<String> fetchExchangeRatesForCurrencies(String baseCurrency, String targetCurrencies) throws Exception;
}
