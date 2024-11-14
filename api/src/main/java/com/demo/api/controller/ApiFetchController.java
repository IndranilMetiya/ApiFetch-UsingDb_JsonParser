package com.demo.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.api.service.ApiService;

@RestController
@RequestMapping("/api")
public class ApiFetchController {

    @Autowired
    private ApiService apiService;

  
    @GetMapping("/data")
    public ResponseEntity<Object> fetchMultipleData(
            @RequestParam String apiName,
            @RequestParam List<String> desiredValues,
            @RequestParam(required = false) Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        try {
            Map<String, Object> result = apiService.fetchData(apiName, desiredValues, params);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

