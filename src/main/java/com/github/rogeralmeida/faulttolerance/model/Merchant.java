/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.model;

import javax.lang.model.element.Name;

import lombok.Data;

@Data
public class Merchant {
    private String name;
    private Integer foursquareCheckins;
    private Integer yelpRating;
}
