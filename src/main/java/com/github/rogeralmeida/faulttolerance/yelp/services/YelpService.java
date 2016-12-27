/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.yelp.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.rogeralmeida.faulttolerance.yelp.commands.SearchBusinessCommand;
import com.github.rogeralmeida.faulttolerance.yelp.model.Business;

@Service
public class YelpService {

    @Autowired
    private RestTemplate restTemplate;

    public Set<Business> findBusinessReview(String businessName) {
        SearchBusinessCommand searchBusinessCommand = new SearchBusinessCommand(businessName, restTemplate);
        Set<Business> execute = searchBusinessCommand.execute();
        return execute;
    }
}
