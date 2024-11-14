package com.demo.api.fetchcontrollertest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
public class ApiFetchControllerTest {

    @BeforeAll
    public static void setUp() {
        // Set the base URI for the API
        RestAssured.baseURI = "http://localhost:8082"; 
    }



    @Test
    public void testFetchDataWithMultipleDesiredValues() {
        String apiName = "orderService";
        List<String> desiredValues = Arrays.asList("processor");

        Response response = given()
                .param("apiName", apiName)
                .param("desiredValues", String.join(",", desiredValues)) // Joined desired values by comma
                .when()
                .get("/api/data");

        // Validate the status code
        response.then().statusCode(200);

        // Validate the response body
        response.then().body("processor", equalTo("Snapdragon 888"));
    }

    @Test
    public void testInvalidApiName() {
        // Define an invalid API name
        String apiName = "invalidApi";

        // Send GET request with invalid API name and expect 500 status
        given()
                .param("apiName", apiName)
                .param("desiredValues", "processor")
                .when()
                .get("/api/data")
                .then()
                .statusCode(500)
                .body(containsString("API configuration not found"));
    }
}

