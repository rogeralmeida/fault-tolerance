/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.controllers;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.rogeralmeida.faulttolerance.model.foursquare.Venue;
import com.github.rogeralmeida.faulttolerance.services.FourSquareService;

@Controller
public class SearchController {

    @Autowired
    private FourSquareService fourSquareService;

    @RequestMapping("/search")
    public String search(@RequestParam(value = "query", required = false) String query, Model model) {
        Set<Venue> venues = fourSquareService.searchVenues(query);
        model.addAttribute("venues", venues);
        return "searchResults";
    }

}
