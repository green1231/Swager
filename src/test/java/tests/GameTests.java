package tests;

import io.restassured.http.ContentType;
import models.games.GamesRoot;
import models.users.Info;
import models.users.JwtAuthData;
import models.users.UserRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.GameGenerator;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class GameTests {
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
                .statusCode(201);

        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("http://85.192.34.140:8080/api/login")
                .then().log().all()
                .statusCode(200)
                .extract().jsonPath().getString("token");
        Assertions.assertNotNull(token);

        Info info = given().contentType(ContentType.JSON)
                 .auth().oauth2(token)
                 .body(GameGenerator.generateGameFullData())
                 .post("http://85.192.34.140:8080/api/user/games")
                 .then().log().all()
                 .statusCode(201)
                 .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus()) ;
        Assertions.assertEquals("Game created", info.getMessage()) ;

    }
    @Test
    public void getGames(){
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
                .statusCode(201);

         JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());

         String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("http://85.192.34.140:8080/api/login")
                .then().log().all()
                .statusCode(200)
                .extract().jsonPath().getString("token");
         Assertions.assertNotNull(token);

         given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(GameGenerator.generateGameFullData())
                .post("http://85.192.34.140:8080/api/user/games")
                .then().log().all()
                .statusCode(201);

         int statusCode = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .get("http://85.192.34.140:8080/api/user/games")
                .then().log().all()
                .extract().statusCode();
         Assertions.assertEquals(200, statusCode);


    }
    @Test
    public void getGamesFromID(){
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
                .statusCode(201);

        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());

        String token = given().contentType(ContentType.JSON)
                .body(authData)
                .post("http://85.192.34.140:8080/api/login")
                .then().log().all()
                .statusCode(200)
                .extract().jsonPath().getString("token");
        Assertions.assertNotNull(token);

        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(GameGenerator.generateGameFullData())
                .post("http://85.192.34.140:8080/api/user/games")
                .then().log().all()
                .statusCode(201);

        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .get("http://85.192.34.140:8080/api/user/games")
                .then().log().all()
                .extract().statusCode();



    }
}





