/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.foursquare.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.rogeralmeida.faulttolerance.foursquare.command.SearchVenueCommand;
import com.github.rogeralmeida.faulttolerance.foursquare.model.Venue;

import lombok.extern.java.Log;

@Service
@Log
public class FourSquareService {

    @Autowired
    private RestTemplate restTemplate;

    public Set<Venue> searchVenues(String name) {

        SearchVenueCommand searchVenueCommand = new SearchVenueCommand(name, restTemplate);

        return searchVenueCommand.execute();
    }

}
