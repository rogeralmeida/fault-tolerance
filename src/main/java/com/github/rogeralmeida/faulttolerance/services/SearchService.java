/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.services;

import java.util.*;

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

    public Collection<Merchant> findMerchants(String query) {
        Set<Venue> venues = fourSquareService.searchVenues(query);
        Set<Business> businessReview = yelpService.findBusinessReview(query);

        Map<String, Merchant> merchants = new HashMap<>();

        mergeFourSquareResults(venues, merchants);
        mergeYelpResults(businessReview, merchants);

        return merchants.values();
    }

    private void mergeYelpResults(Set<Business> businessReview, Map<String, Merchant> merchants) {
        businessReview.forEach(business -> {
            Merchant merchant = getMerchant(merchants, business.getName());
            merchant.setYelpRating(business.getRating());
            merchants.put(merchant.getName(), merchant);
        });
    }

    private void mergeFourSquareResults(Set<Venue> venues, Map<String, Merchant> merchants) {
        venues.forEach(venue -> {
            Merchant merchant = getMerchant(merchants, venue.getName());
            merchant.setFoursquareCheckins(venue.getStats().getCheckinsCount());
            merchants.put(merchant.getName(), merchant);
        });
    }

    private Merchant getMerchant(Map<String, Merchant> merchants, String name) {
        Merchant merchant = merchants.get(name);
        if (merchant == null) {
            merchant = new Merchant();
            merchant.setName(name);
        }
        return merchant;
    }
}
