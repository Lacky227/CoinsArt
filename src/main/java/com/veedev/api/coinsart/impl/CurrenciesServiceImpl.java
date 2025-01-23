package com.veedev.api.coinsart.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.veedev.api.coinsart.service.CurrenciesService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CurrenciesServiceImpl implements CurrenciesService {

    private final RestTemplate restTemplate;

    @Value("${currency.api.url}")
    private String apiURL;

    @Value("${currency.api.key}")
    private String apiKey;

    @Override
    public ResponseEntity<String> fetchExchangeRates(String baseCurrency, int amount) throws Exception {
        String url = String.format("%s/latest?apikey=%s&base_currency=%s", apiURL, apiKey, baseCurrency);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = (ObjectNode) mapper.readTree(response.getBody());
            ObjectNode dataNode = (ObjectNode) node.get("data");
            dataNode.fields().forEachRemaining(entry -> {
                ObjectNode currenciesNode = (ObjectNode) entry.getValue();
                double value = currenciesNode.get("value").asDouble();
                currenciesNode.put("value", value * amount);
            });
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(node));
        }else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch exchange rates.");
        }
    }

    @Override
    public ResponseEntity<String> fetchExchangeRatesForCurrencies(String baseCurrency, String targetCurrencies, int amount) throws Exception {
        try {
            String encodeTargetCurrencies = URLEncoder.encode(targetCurrencies, StandardCharsets.UTF_8).replace("%2C", ",");
            String url = String.format("%s/latest?apikey=%s&base_currency=%s&currencies=%s", apiURL, apiKey, baseCurrency, encodeTargetCurrencies);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = (ObjectNode) mapper.readTree(response.getBody());
                ObjectNode dataNode = (ObjectNode) node.get("data");
                dataNode.fields().forEachRemaining(entry -> {
                    ObjectNode currenciesNode = (ObjectNode) entry.getValue();
                    double originalValue = currenciesNode.get("value").asDouble();
                    currenciesNode.put("value", originalValue * amount);
                });
                return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(node));
            }else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch exchange rates.");
            }

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }
}
