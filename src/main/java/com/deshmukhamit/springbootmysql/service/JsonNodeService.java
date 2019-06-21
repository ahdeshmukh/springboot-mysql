package com.deshmukhamit.springbootmysql.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class JsonNodeService {
    public Object getValue(JsonNode jsonNode, String key) {
        try {
            JsonNode value = jsonNode.get(key);
            if(value.isTextual()) {
                return value.asText();
            }
            if(value.isInt()) {
                return value.asInt();
            }
            if(value.isLong()) {
                return value.asLong();
            }


        } catch (NullPointerException ex) {
            return null;
        }

        return null;
    }
}
