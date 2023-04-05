package org.gb;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.Before;

import static io.restassured.RestAssured.given;

abstract class AbstractTest {

    protected String login = "kokorevpv";
    protected String password = "e0ddbf337b";
    protected String authHeader;
    protected String id;
    @Before
    public void setup() {
        RestAssured.baseURI = "https://test-stand.gb.ru";
        RestAssured.port = 443;
        RestAssured.registerParser("text/plain", Parser.JSON);

        var response = given()
                .contentType("multipart/form-data")
                .multiPart("username", login)
                .multiPart("password", password)
                .post("/gateway/login");

        authHeader = response.then()
                .extract().jsonPath().get("token");

        id = response.then()
                .extract().jsonPath().get("id").toString();
    }
}
