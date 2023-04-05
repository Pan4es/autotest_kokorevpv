package org.gb;

import io.restassured.http.Header;
import org.hamcrest.core.AllOf;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNull.notNullValue;

public class PostsMine extends AbstractTest {
    @Test()
    public void getMinePostsWithoutAuth() {
        given()
                .get("/api/posts?sort=createdAt&order=ASC&page=1")
                .then()
                .statusCode(401);
    }

    @Test
    public void getMinePostsMinusPage() {
        given()
                .header(new Header("X-Auth-Token", authHeader))
                .get("/api/posts?sort=createdAt&order=ASC&page=-1")
                .then()
                .statusCode(400);
    }

    @Test
    public void getMinePostsBadOrder() {
        given()
                .header(new Header("X-Auth-Token", authHeader))
                .get("/api/posts?sort=createdAt&order=123&page=1")
                .then()
                .statusCode(400);
    }

    @Test
    public void getMinePostsBadSort() {
        given()
                .header(new Header("X-Auth-Token", authHeader))
                .get("/api/posts?sort=123&order=ASC&page=1")
                .then()
                .statusCode(400);
    }

    @Test
    public void getMinePosts() {
        given()
                .header(new Header("X-Auth-Token", authHeader))
                .get("/api/posts?sort=createdAt&order=ASC&page=1")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .body("data.authorId", hasItem(Integer.valueOf(id)));
    }
}
