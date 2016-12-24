/*
 * Copyright (c) 2003 - 2016 Tyro Payments Limited.
 * Lv1, 155 Clarence St, Sydney NSW 2000.
 * All rights reserved.
 */
package com.github.rogeralmeida.faulttolerance.yelp.model;

import lombok.Data;

@Data
public class Business {
    private Integer rating;
    private String price;
    private String phone;
    private String id;
    private Integer review_count;
    private String name;
    private String url;
    private String image_url;
}
