/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.yelp.services;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.support.HttpAccessor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogeralmeida.faulttolerance.yelp.model.Business;
import com.github.rogeralmeida.faulttolerance.yelp.model.SearchBusinessResponse;
import com.github.rogeralmeida.faulttolerance.yelp.model.TokenRequest;
import com.github.rogeralmeida.faulttolerance.yelp.model.TokenResponse;

@Service
public class YelpService {

    @Autowired
    private RestTemplate restTemplate;

    private TokenResponse tokenResponse;

    public Set<Business> findBusinessReview(String businessName) {
        try {
            ensureWeHaveTheAccessToken();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        Map<String, Object> parametersMap = new HashMap<>();
//        parametersMap.put("term", businessName);
//        parametersMap.put("location", "Sydney, New South Whales, Australia");

        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + tokenResponse.getAccess_token());

        HttpEntity<String> stringHttpEntity = new HttpEntity<>("", httpHeaders);

        String url = null;
        try {
            url = "https://api.yelp.com/v3/businesses/search?location=" + URLEncoder.encode("Sydney, NSW, Australia", "UTF-8") + "&term="+
                    URLEncoder.encode(businessName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<SearchBusinessResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                stringHttpEntity, SearchBusinessResponse.class);
        SearchBusinessResponse searchBusinessResponse = responseEntity.getBody();
        return searchBusinessResponse.getBusinesses();
    }

    private void ensureWeHaveTheAccessToken() throws JsonProcessingException {
        if (tokenResponse == null) {
            String yelp_client_id = "0UuMWDaXUQNh3QkOR5mS-w"; //System.getenv("YELP_CLIENT_ID");
            String yelp_secret = "wGTfpUrrU2iHMwJNpzZJzvQbRf7A40cAKWo6ULKQH9aiQ0KJHIx4vPAOmHw7OPyo"; //System.getenv("YELP_SECRET");


            MultiValueMap<String, String> fields = new LinkedMultiValueMap<>();
            fields.add("grant_type", "client_credentials");
            fields.add("client_id", yelp_client_id);
            fields.add("client_secret", yelp_secret);

            HttpHeaders httpHeaders = getHttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(fields, httpHeaders);

            ResponseEntity<TokenResponse> exchange = restTemplate.exchange("https://api.yelp.com/oauth2/token", HttpMethod.POST, requestEntity, TokenResponse.class);
            tokenResponse = exchange.getBody();
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setAcceptCharset(Collections.singletonList(Charset.defaultCharset()));
        return httpHeaders;
    }
}
