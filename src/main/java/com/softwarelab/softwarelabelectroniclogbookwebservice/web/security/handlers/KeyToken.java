package com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Fri, 14/05/2021.
 */
@Data
@Builder
public class KeyToken {
    private String key;
    private String token;
}
