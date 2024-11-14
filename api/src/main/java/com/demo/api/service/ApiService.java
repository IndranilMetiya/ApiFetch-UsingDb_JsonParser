package com.demo.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.api.entity.ApiConfig;
import com.demo.api.repository.ApiConfigRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@Service
public class ApiService {

    @Autowired
    private ApiConfigRepository apiConfigRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> fetchData(String apiName, List<String> desiredValues, Map<String, String> params) throws Exception {
        List<ApiConfig> apiConfigs = apiConfigRepository.findByApiNameAndDesiredValues(apiName, desiredValues);
        
        if (apiConfigs.isEmpty()) {
            throw new Exception("API configuration not found for the given desired values");
        }

        String url = buildUrl(apiConfigs.get(0), params); // Us the first config's URL since they share the same base
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        Map<String, Object> result = new HashMap<>();
        
		for (ApiConfig config : apiConfigs) {
			Object value = jsonContext.read(config.getJsonQuery());

			if (value instanceof List) {
				List<?> valueList = (List<?>) value;
				String joinedValue = String.join(", ",
						valueList.stream().map(Object::toString).collect(Collectors.toList()));
				result.put(config.getDesiredValue(), joinedValue);
			} else {
				result.put(config.getDesiredValue(), value);
			}
		}  
     
        return result;
    }

    private String buildUrl(ApiConfig config, Map<String, String> params) {
        String url = config.getBaseUrl();
        
        if (config.getPathParams() != null) {
            JsonArray pathParams = JsonParser.parseString(config.getPathParams()).getAsJsonArray();
            for (JsonElement param : pathParams) {
                String paramName = param.getAsString();
                url = url.replace("{" + paramName + "}", params.get(paramName));
            }
        }
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (config.getQueryParams() != null) {
            JsonArray queryParams = JsonParser.parseString(config.getQueryParams()).getAsJsonArray();
            for (JsonElement param : queryParams) {
                String paramName = param.getAsString();
                builder.queryParam(paramName, params.get(paramName));
            }
        }
        
        return builder.toUriString();
    }
}


