package org.gb;

import io.restassured.http.Header;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNull.notNullValue;


public class PostsNotMine extends AbstractTest {

    @Test()
    public void getNotMinePostsWithoutAuth() {
        given()
                .get("/api/posts?owner=notMe&sort=createdAt&order=ASC&page=1")
        .then()
                .statusCode(401);
    }

    @Test
    public void getNotMinePostsMinusPage() {
        given()
                .header(new Header("X-Auth-Token", authHeader))
                .get("/api/posts?owner=notMe&sort=createdAt&order=ASC&page=-1")
        .then()
                .statusCode(400);
    }

    @Test
    public void getNotMinePostsBadOrder() {
        given()
                .header(new Header("X-Auth-Token", authHeader))
                .get("/api/posts?owner=notMe&sort=createdAt&order=123&page=1")
        .then()
                .statusCode(400);
    }

    @Test
    public void getNotMinePostsBadSort() {
        given()
                .header(new Header("X-Auth-Token", authHeader))
                .get("/api/posts?owner=notMe&sort=123&order=ASC&page=1")
        .then()
                .statusCode(400);
    }

    @Test
    public void getNotMinePosts() {
        given()
            .header(new Header("X-Auth-Token", authHeader))
            .get("/api/posts?owner=notMe&sort=createdAt&order=ASC&page=1")
        .then()
            .statusCode(200)
            .body(notNullValue())
            .body("data.authorId", new IsNot(hasItem(id)));
    }
}
