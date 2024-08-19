package tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.users.Info;
import models.users.JwtAuthData;
import models.users.UserRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class UserTests {

    private Random random = new Random();

    private UserRoot getTestUser() {
        int randomNamber = Math.abs(random.nextInt());
        return UserRoot.builder()
                .login("deeerr" + randomNamber)
                .pass("23ededasdas")
                .build();
    }

    @BeforeAll
    static void setUp() {

        RestAssured.baseURI = "http://85.192.34.140:8080/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    private String getToken(String login, String password) {

        JwtAuthData authData = new JwtAuthData(login, password);

        return given().contentType(ContentType.JSON)
                .body(authData)
                .post("/login")
                .then()
                .extract().jsonPath().getString("token");

    }

    private ValidatableResponse regUser(UserRoot user) {

        return given().contentType(ContentType.JSON)
                .body(user)
                .post("/signup")
                .then();

    }

    @Test
    public void positiveRegistration() {
        UserRoot user = getTestUser();

        Info info = regUser(user)
                .statusCode(201)
                .extract()
                .jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("User created", info.getMessage());

    }

    @Test
    public void positiveAdminAuth() {

        String token = getToken("admin", "admin");
        Assertions.assertNotNull(token);

    }

    @Test
    public void positiveNewUserAuth() {

        UserRoot user = getTestUser();

        regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());
        Assertions.assertNotNull(token);
    }

    @Test
    public void positiveGetUserInfo() {

        UserRoot user = getTestUser();

        regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());

        UserRoot response = given().auth().oauth2(token)
                .get("/user")
                .then()
                .statusCode(200)
                .extract().body().as(UserRoot.class);
        Assertions.assertEquals(user.getLogin(), response.getLogin());
        Assertions.assertEquals(user.getPass(), response.getPass());

    }

    @Test
    public void updatingUserPassword() {

        UserRoot user = getTestUser();

        regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());

        Map<String, String> password = new HashMap<>();
        password.put("password", "234234234");

        Info info = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(password)
                .put("/user")
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("info", Info.class);
        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("User password successfully changed", info.getMessage());

    }

    @Test
    public void deletUser() {

        UserRoot user = getTestUser();

        regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());

        Info info = given().auth().oauth2(token)
                .delete("/user")
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("User successfully deleted", info.getMessage());
    }


}
