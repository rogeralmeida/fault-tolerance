/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.foursquare.model;

import lombok.Data;

@Data
public class Stats {
    private Integer checkinsCount;
    private Integer usersCount;
    private Integer tipCount;
}
