package utils;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecProvider {

    // Basic ResponseSpecification with status code validation
    public static ResponseSpecification getBasicResponseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    // ResponseSpecification with status code and header validation
    public static ResponseSpecification getResponseSpecWithHeaders(int statusCode, String headerName, String headerValue) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectHeader(headerName, headerValue)
                .log(LogDetail.ALL)
                .build();
    }

    // ResponseSpecification with status code and body validation
    public static ResponseSpecification getResponseSpecWithBody(int statusCode, String jsonPath, String expectedValue) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectBody(jsonPath, org.hamcrest.Matchers.equalTo(expectedValue))
                .log(LogDetail.ALL)
                .build();
    }
}
