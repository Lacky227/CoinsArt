package com.veedev.api.coinsart.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CurrenciesService {
    ResponseEntity<String> getCurrencies(String currency);
    ResponseEntity<String> getTradeCurrencies(String currency, String tradeCurrency);
}
