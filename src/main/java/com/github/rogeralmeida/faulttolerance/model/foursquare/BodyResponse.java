/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.model.foursquare;

import java.util.Set;

import lombok.Data;

@Data
public class BodyResponse {

    private Set<Venue> venues;
}
