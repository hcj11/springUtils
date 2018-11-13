package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
public class CustomMediaType extends MediaType {
    //  phf 格式的json
    public static final String PHF_JSON_VALUE = "phf/json";

    public CustomMediaType(String type,String subType) {
        super(type,subType);
    }

}
