package com.interseguro.test.services;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("apiService")
public class ApiService {
    @Value("${interseguro.endpoint}")
    private String ENDPOINT;
    @Value("${interseguro.apikey}")
    private String API_KEY;
    @Value("${interseguro.provider}")
    private String PROVIDER;
    
    public Map<String, Object> getSessionKey() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> result = new HashMap<>();  
        Map<String, Object> data = new HashMap<>();

        JSONObject merchant = new JSONObject();
        merchant.put("MDD1","0780000001");
        
        JSONObject fraud = new JSONObject();
        fraud.put("clientIP","10.29.1.75");
        fraud.put("merchantDefineData",merchant);

        JSONObject request = new JSONObject();
        request.put("amount",1.00);
        request.put("antifraud",fraud);
        request.put("currency","PEN");
        request.put("channel","web");
        request.put("recurrentMaxAmount",0);
        
        try {
            HttpResponse<String> response_bus = Unirest.post(ENDPOINT)
                .header("X-ApiKey", API_KEY)
                .header("X-Provider", PROVIDER)
                .header("content-type", "application/json")
                .header("accept", "application/json")
                .body(request)
                .asString();

            ObjectMapper mapper = new ObjectMapper();
            result.putAll(mapper.readValue(response_bus.getBody(), Map.class));

            if (null != result.get("data")) {
                data = (Map) result.get("data");
                response.put("sessionKey", data.get("sessionKey"));
            } else {
                response = result;
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            e.printStackTrace();
        }

        return response;
    }
}