package org.gb;

import jdk.jfr.Description;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoginTest extends AbstractTest {
    private String path = "/gateway/login";

    @Test
    @Description("Успешная авторизация")
    public void loginSuccess(){
        given()
                .contentType("multipart/form-data")
                .multiPart("username", login)
                .multiPart("password", password)
                .post(path)
        .then()
                .assertThat()
                .statusCode(200)
                .body("username", equalTo(login));
    }

    @Test
    @Description("Без пароля")
    public void loginWithoutPassword(){
        given()
                .contentType("multipart/form-data")
                .multiPart("username", login)
                .multiPart("password", "")
                .post(path)
                .then().assertThat()
                .statusCode(401);
    }

    @Test
    @Description("Без логина")
    public void loginWithoutLogin(){
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "")
                .multiPart("password", password)
                .post(path)
                .then().assertThat()
                .statusCode(401);
    }
}
