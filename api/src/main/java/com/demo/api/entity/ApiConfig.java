package com.demo.api.entity;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "api_config")
public class ApiConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "api_name", nullable = false)
    private String apiName;

    @Column(name = "base_url", nullable = false)
    private String baseUrl;

    @Column(name = "path_params")
    private String pathParams; // Stores JSON array of path parameters

    @Column(name = "query_params")
    private String queryParams; // Stores JSON array of query parameters

    @Column(name = "json_query", nullable = false)
    private String jsonQuery; // JSONPath expression to extract specific data

    @Column(name = "desired_value", nullable = false)
    private String desiredValue; // Specifies the data label (e.g., bookName, authorName)

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPathParams() {
        return pathParams;
    }

    public void setPathParams(String pathParams) {
        this.pathParams = pathParams;
    }

    public String getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
    }

    public String getJsonQuery() {
        return jsonQuery;
    }

    public void setJsonQuery(String jsonQuery) {
        this.jsonQuery = jsonQuery;
    }

    public String getDesiredValue() {
        return desiredValue;
    }

    public void setDesiredValue(String desiredValue) {
        this.desiredValue = desiredValue;
    }

	@Override
	public String toString() {
		return "ApiConfig [id=" + id + ", apiName=" + apiName + ", baseUrl=" + baseUrl + ", pathParams=" + pathParams
				+ ", queryParams=" + queryParams + ", jsonQuery=" + jsonQuery + ", desiredValue=" + desiredValue + "]";
	}
    
    public String toJsonString() {
    	ObjectMapper mapper=new ObjectMapper();
    	
    	try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			
			return "Error to parse to json-->string(json)";
		}
    	
    }
}


