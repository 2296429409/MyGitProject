package com.icis.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtils {
    private static final ObjectMapper om=new ObjectMapper();
    public static String jsonString(Object o){
        try {
            return om.writeValueAsString(o);
        } catch (Exception e) {
            return null;
        }
    }
}
