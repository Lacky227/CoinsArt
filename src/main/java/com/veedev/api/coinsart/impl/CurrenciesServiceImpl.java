package com.veedev.api.coinsart.impl;

import com.veedev.api.coinsart.service.CurrenciesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CurrenciesServiceImpl implements CurrenciesService {

    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> getCurrencies(String currency) {
        String url = "https://api.currencyapi.com/v3/latest?apikey=cur_live_2LAzDfXRBCMq305AmSqTsQqwAImjtWAxOqA2VbQK&base_currency=" + currency;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @Override
    public ResponseEntity<String> getTradeCurrencies(String currency, String tradeCurrency) {
        String url = "https://api.currencyapi.com/v3/latest?apikey=cur_live_2LAzDfXRBCMq305AmSqTsQqwAImjtWAxOqA2VbQK&base_currency=" + currency +"&currencies="+tradeCurrency;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
