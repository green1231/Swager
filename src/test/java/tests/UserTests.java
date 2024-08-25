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
import service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class UserTests {
    private static UserService userService;
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
        userService = new UserService();
    }

    private String getToken(String login, String password) {

        JwtAuthData authData = new JwtAuthData(login, password);

        return given().contentType(ContentType.JSON)
                .body(authData)
                .post("/login")
                .then()
                .extract().jsonPath().getString("token");
    }



    @Test
    public void positiveRegistration() {
        UserRoot user = getTestUser();

        Info info = userService.regUser(user)
                .statusCode(201)
                .extract()
                .jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("User created", info.getMessage());
    }

    @Test
    public void positiveAdminAuth() {
        UserRoot user = UserRoot.builder()
                .login("admin")
                .pass("admin")
                .build();
        String token = userService
                .auth(user)
                .extract()
                .jsonPath().getString("token");
        Assertions.assertNotNull(token);

    }

    @Test
    public void positiveNewUserAuth() {

        UserRoot user = getTestUser();

        userService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());
        Assertions.assertNotNull(token);
    }

    @Test
    public void positiveGetUserInfo() {

        UserRoot user = getTestUser();

        userService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());

        UserRoot response = userService.getUser(token)
                .statusCode(200)
                .extract().body().as(UserRoot.class);
        Assertions.assertEquals(user.getLogin(), response.getLogin());
        Assertions.assertEquals(user.getPass(), response.getPass());

    }

    @Test
    public void updatingUserPassword() {

        UserRoot user = getTestUser();

        userService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());

        Info info = userService.updUserPass(user.getPass(),token)
                .statusCode(200)
                .extract().jsonPath().getObject("info", Info.class);
        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("User password successfully changed", info.getMessage());

    }

    @Test
    public void deletUser() {

        UserRoot user = getTestUser();

        userService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());

        Info info = userService.delUser(token)
                .statusCode(200)
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("User successfully deleted", info.getMessage());
    }


}
