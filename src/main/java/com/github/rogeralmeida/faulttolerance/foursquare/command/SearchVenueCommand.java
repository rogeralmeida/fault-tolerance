/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.foursquare.command;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.github.rogeralmeida.faulttolerance.foursquare.model.BodyResponse;
import com.github.rogeralmeida.faulttolerance.foursquare.model.Venue;
import com.github.rogeralmeida.faulttolerance.foursquare.model.VenueSearchResponse;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import lombok.extern.java.Log;

@Log
public class SearchVenueCommand extends HystrixCommand<Set<Venue>> {

    private static final String URL = "https://api.foursquare.com/v2/venues/search?near=Sydney,AU" +
                "&client_id=3TKSCYPHHW4MEHWS1QXC343USZXQN11GIJTHQRAHDL3XEUNX" +
                "&v=20161223" +
                "&client_secret=V5XQRGKG5PS41BZ5L3J4S00RM4RCQDDJ0UOIPCQ2BU5ZVYYN&query=%s";

    private final String name;
    private final RestTemplate restTemplate;

    public SearchVenueCommand(String name, RestTemplate restTemplate) {
        super(
                Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FourSquare"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(100)
                        .withMetricsRollingPercentileWindowInMilliseconds(3000)

                ).andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                        .withCoreSize(250)
                )
                );
        this.name = name;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Set<Venue> run() throws Exception {
        ResponseEntity<VenueSearchResponse> venueSearchResponseResponseEntity = null;
        try {
            venueSearchResponseResponseEntity = restTemplate.getForEntity(String.format(URL, URLEncoder.encode(name, "UTF-8")), VenueSearchResponse.class);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        VenueSearchResponse venueSearchResponse = venueSearchResponseResponseEntity.getBody();
        BodyResponse response = venueSearchResponse.getResponse();
        log.info("Response: " + response.toString());

        return response.getVenues();
    }

    @Override
    protected Set<Venue> getFallback() {
        return new HashSet<>();
    }
}
