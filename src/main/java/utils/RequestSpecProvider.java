package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecProvider {

    // Basic RequestSpecification
    public static RequestSpecification getBasicSpec(String baseUri) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setRelaxedHTTPSValidation()
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    // RequestSpecification with Authorization Header
    public static RequestSpecification getAuthSpec(String baseUri, String token) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addHeader("authorization", token)
                .log(LogDetail.ALL)
                .build();
    }

    // RequestSpecification with Query Parameters
    public static RequestSpecification getQueryParamSpec(String baseUri, String token, String paramKey, String paramValue) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addHeader("authorization", token)
                .addQueryParam(paramKey, paramValue)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    // RequestSpecification with Path Parameters
    public static RequestSpecification getPathParamSpec(String baseUri, String token, String paramKey, String paramValue) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addHeader("authorization", token)
                .addPathParam(paramKey, paramValue)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    // RequestSpecification with MultiPart Data
    public static RequestSpecification getMultiPartSpec(String baseUri, String token, String filePath) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .addHeader("authorization", token)
                .setContentType(ContentType.MULTIPART)
                .addMultiPart("file", new java.io.File(filePath))
                .log(LogDetail.ALL)
                .build();
    }
}
