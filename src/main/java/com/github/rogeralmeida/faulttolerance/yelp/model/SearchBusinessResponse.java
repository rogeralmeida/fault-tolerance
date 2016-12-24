/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.yelp.model;

import java.util.Set;

import lombok.Data;

@Data
public class SearchBusinessResponse {
    private Integer total;
    private Set<Business> businesses;
}
