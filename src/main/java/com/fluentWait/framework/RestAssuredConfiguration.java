package com.fluentWait.framework;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;

public class RestAssuredConfiguration {
    @BeforeSuite(alwaysRun = true)
        public void configure(){
        RestAssured.baseURI = "https://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/SprintRestServices";
    }

    public RequestSpecification getRequestSpecification() {
        return RestAssured.given().contentType(ContentType.JSON);
    }
}
