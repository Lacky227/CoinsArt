package com.veedev.api.coinsart.impl;

import com.veedev.api.coinsart.service.CurrenciesService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CurrenciesServiceImpl implements CurrenciesService {

    private final RestTemplate restTemplate;

    @Value("${currency.api.url}")
    private String apiURL;

    @Value("${currency.api.key}")
    private String apiKey;

    @Override
    public ResponseEntity<String> fetchExchangeRates(String baseCurrency) {
        String url = String.format("%s/latest?apikey=%s&base_currency=%s", apiURL, apiKey, baseCurrency);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @Override
    public ResponseEntity<String> fetchExchangeRatesForCurrencies(String baseCurrency, String targetCurrencies) throws Exception {
        try {
            String encodeTargetCurrencies = URLEncoder.encode(targetCurrencies, StandardCharsets.UTF_8).replace("%2C", ",");
            String url = String.format("%s/latest?apikey=%s&base_currency=%s&currencies=%s", apiURL, apiKey, baseCurrency, encodeTargetCurrencies);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
}
