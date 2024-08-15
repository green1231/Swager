package tests;

import io.restassured.http.ContentType;
import models.User.Info;
import models.User.JwtAuthData;
import models.User.UserRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class GameController {
    @Test
    public void addGAme() {
        Random random = new Random();
        int randomNamber = Math.abs(random.nextInt());

        UserRoot user = UserRoot.builder()
                .login("deeerr" + randomNamber)
                .pass("23ededasdas")
                .build();

        given().contentType(ContentType.JSON)
                .body(user)
                .post("http://85.192.34.140:8080/api/signup")
                .then().log().all()
                .statusCode(201)
                .extract()
                .jsonPath().getObject("info", Info.class);

        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("http://85.192.34.140:8080/api/login")
                .then().log().all()
                .statusCode(200)
                .extract().jsonPath().getString("token");
        Assertions.assertNotNull(token);


    }
}





