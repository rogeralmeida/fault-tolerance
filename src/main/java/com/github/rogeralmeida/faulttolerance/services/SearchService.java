/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rogeralmeida.faulttolerance.foursquare.model.Venue;
import com.github.rogeralmeida.faulttolerance.foursquare.services.FourSquareService;
import com.github.rogeralmeida.faulttolerance.model.Merchant;
import com.github.rogeralmeida.faulttolerance.yelp.model.Business;
import com.github.rogeralmeida.faulttolerance.yelp.services.YelpService;

@Service
public class SearchService {

    @Autowired
    private FourSquareService fourSquareService;

    @Autowired
    private YelpService yelpService;

    public Set<Merchant> findMerchants(String query) {
        Set<Venue>      venues              = fourSquareService.searchVenues(query);
        Set<Business>   businessReview      = yelpService.findBusinessReview(query);
        return new HashSet<>();
    }
}
