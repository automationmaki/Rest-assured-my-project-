package com.fluentWait.test;

import com.fluentWait.framework.RestAssuredConfiguration;
import com.fluentWait.test.bin.EmployeeBin;
import com.fluentWait.test.common.EndPoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Employee {


    @Test
    public void validateEmployee(){
        given().get("https://localhost:8080/SprintRealServices/employee/getEmployee").then().statusCode(200).log().all();
    }


    @Test(groups = "demo")
    public void validateEmployee2(){
        given().get(EndPoint.GET_EMPLOYEE).then().statusCode(200).log().all();
    }

    @Test(groups = "demo")
    public void validateQueryParameter(){
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.queryParam("emloyeeId", 100).log().all();
        given().spec(requestSpecification).get(EndPoint.GET_EMPLOYEE_QUERY_PARAM).
                then().statusCode(200).log().all();

        //getting Response
        Response response = given().spec(requestSpecification).get(EndPoint.GET_EMPLOYEE_QUERY_PARAM);
        //Inline Validation
        //1.Hard Assertion
        response.then().body("firstName", equalTo("Fluent")).body("lastName", equalTo("Wait"))
                .body("address", equalTo("New York"));
        //2.Soft Assertion
//        response.then().body("firstName", equalTo("Alex")), "lastName", equalTo("Wait"), "address", equalTo("CA"));
        //Path Validation
        //1.Hard Assertion
        Assert.assertEquals(response.path("firstName"), "Fluent");
        Assert.assertEquals(response.path("lastName"), "Wait");
        //2.Soft Assertion
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.path("firstName"), "Fluent");
        softAssert.assertEquals(response.path("lastName"), "Wait");
        softAssert.assertEquals(response.path("address"), "New York");
        softAssert.assertAll();
        //Java Object
        EmployeeBin employeeBin = response.as(EmployeeBin.class);
        //1.Hard Assertion
        Assert.assertEquals(employeeBin.getFirstName(), "Fluent");
        //2.Soft Assertion
        SoftAssert newSoftAssert = new SoftAssert();
        newSoftAssert.assertEquals(employeeBin.getFirstName(), "Fluent");
        newSoftAssert.assertEquals(employeeBin.getLastName(), "Wait");
        newSoftAssert.assertEquals(employeeBin.getAddress(), "New York");
        newSoftAssert.assertAll();

    }

    @Test(groups = "demo")
    public void validatePathParameter(){
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.pathParam("emloyeeId", 100).log().all();
        given().spec(requestSpecification).get(EndPoint.GET_EMPLOYEE_PATH_PARAM).
                then().statusCode(200).log().all();
    }

    @Test(groups = "demo")
    public void validateFormParameters(){
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.accept(ContentType.JSON).formParams("employeeID", 100).log().all();
        given().spec(requestSpecification).get(EndPoint.POST_EMPLOYEE_PARAM).
                then().statusCode(200).log().all();
    }
}
