package com.sevenbridges;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

public class Task01_GetAmazonRequest {

    @Test

    public void simpleGetAmazonRequest(){

        Response response = RestAssured.get("https://www.amazon.com/");

      //  response.prettyPeek();

        Assertions.assertEquals(response.statusCode(),200);

    }

}
