/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.controllers;


import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.rogeralmeida.faulttolerance.model.Merchant;
import com.github.rogeralmeida.faulttolerance.services.SearchService;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/")
    public String search(@RequestParam(value = "query", required = false) String query, Model model) {
        Collection<Merchant> merchants = searchService.findMerchants(query);
        model.addAttribute("merchants", merchants);
        return "searchResults";
    }

}
