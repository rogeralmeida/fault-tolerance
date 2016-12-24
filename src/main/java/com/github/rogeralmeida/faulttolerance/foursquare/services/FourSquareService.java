/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.foursquare.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.rogeralmeida.faulttolerance.foursquare.model.BodyResponse;
import com.github.rogeralmeida.faulttolerance.foursquare.model.Venue;
import com.github.rogeralmeida.faulttolerance.foursquare.model.VenueSearchResponse;

import lombok.extern.java.Log;

@Service
@Log
public class FourSquareService {



    public static final String URL = "https://api.foursquare.com/v2/venues/search?near=Sydney,AU" +
            "&client_id=3TKSCYPHHW4MEHWS1QXC343USZXQN11GIJTHQRAHDL3XEUNX" +
            "&v=20161223" +
            "&client_secret=V5XQRGKG5PS41BZ5L3J4S00RM4RCQDDJ0UOIPCQ2BU5ZVYYN&query=%s";
    @Autowired
    private RestTemplate restTemplate;

    public Set<Venue> searchVenues(String name) {

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

}
