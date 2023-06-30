package com.spotify.oauth2.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.spotify.oauth2.api.Route.BASE_PATH;

public class SpecBuilder {
    static String access_token = "BQCUyotaug2JnoDnPu881ZlfAhCjmnAJA5rNpffeC7uP5S0a3bbGq-UZYPTRbKzAX4bSJIFtpF5EseAN9T3jabbYwhrRx8llDBSH6zvFpC-grx_jxqV3zRsBf7WnGLxTvjJ1XEuUyEFxRcjB3DAxZPGhcalrKL3UAfMu5vtJf3A9W5nzO2YzbAUvG4dvziQGrv9QQNQR-bSUXweJiNoGG-MMgLNSrXGClahyMn4Dsz8ISXW8MD7YN46yb5U5KM0dcQy34k-5xehji4bF";
    public static RequestSpecification getRequestSpec(){
        return new RequestSpecBuilder().
                setBaseUri(System.getProperty("BASE_URI")).
//                setBaseUri("https://api.spotify.com/").
                setBasePath(BASE_PATH).
                setContentType("application/json;charset=utf-8").
                addFilter(new AllureRestAssured()).
                log(LogDetail.ALL).build();
    }
    public static RequestSpecification getAccountRequestSpec(){
        return new RequestSpecBuilder().
                setBaseUri(System.getProperty("ACCOUNT_BASE_URI")).
//                setBaseUri("https://accounts.spotify.com").
                setContentType(ContentType.URLENC).
                addFilter(new AllureRestAssured()).
                log(LogDetail.ALL).build();
    }
    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).
                build();
    }
}
