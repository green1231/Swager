package tests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.games.UpdateFieldRequest;
import models.games.DlcsItem;
import models.games.GamesRoot;
import models.games.SimilarDlc;
import models.users.Info;
import models.users.JwtAuthData;
import models.users.UserRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.GameService;
import utils.GameGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;


public class GameTests {
    private static final Faker faker = new Faker();
    private static GameService gameService;

    @BeforeAll
    static void setUp() {

        RestAssured.baseURI = "http://85.192.34.140:8080/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        gameService = new GameService();}

    private UserRoot getTestUser() {
        Random random = new Random();
        int randomNumber = Math.abs(random.nextInt());
        return UserRoot.builder()
                .login("deeerr" + randomNumber)
                .pass("23ededasdas")
                .build();
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
    public void addGAme() {

        UserRoot user = getTestUser();

        gameService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());
        Assertions.assertNotNull(token);

        Info info =
                gameService.addGameToUser(token,GameGenerator.generateGameFullData())
                        .statusCode(201)
                        .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("Game created", info.getMessage());
    }

    @Test
    public void getGames() {

        UserRoot user = getTestUser();

        gameService.regUser(user)
                .statusCode(201);

        String token =
                getToken(user.getLogin(), user.getPass());
        Assertions.assertNotNull(token);

        gameService.addGameToUser(token,GameGenerator.generateGameFullData())
                .statusCode(201);

        int statusCode = gameService.getGame(token).log().all()
                .extract().statusCode();
        Assertions.assertEquals(200, statusCode);
    }

    @Test
    public void getGamesFromID() {

        UserRoot user = getTestUser();

        gameService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());
        Assertions.assertNotNull(token);

        GamesRoot response = gameService.addGameToUser(token,GameGenerator.generateGameFullData())
                .statusCode(201)
                .extract().response().jsonPath().getObject("register_data", GamesRoot.class);

        int statusCode = gameService.getGameId(response,token)
                .extract().statusCode();
        Assertions.assertEquals(200, statusCode);
    }

    @Test
    public void updateGameDlcInfo() {

        UserRoot user = getTestUser();

        gameService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());
        Assertions.assertNotNull(token);

        GamesRoot response = gameService.addGameToUser(token,GameGenerator.generateGameFullData())
                .statusCode(201)
                .extract().response().jsonPath().getObject("register_data", GamesRoot.class);

        Info info = gameService.udpateDlcInfo(response,token)
                .log().all()
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("DlC successfully changed", info.getMessage());
    }

    @Test
    public void deleteDlc() {

        UserRoot user = getTestUser();

        gameService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());
        Assertions.assertNotNull(token);

        GamesRoot response = gameService.addGameToUser(token,GameGenerator.generateGameFullData())
                .statusCode(201)
                .extract().response().jsonPath().getObject("register_data", GamesRoot.class);

        Info info = gameService.deleteDlc(response,token).log().all()
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("Game DLC successfully deleted", info.getMessage());
    }

    @Test
    public void deleteGame() {

        UserRoot user = getTestUser();

        gameService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());
        Assertions.assertNotNull(token);

        GamesRoot response = gameService.addGameToUser(token,GameGenerator.generateGameFullData())
                .statusCode(201)
                .extract().response().jsonPath().getObject("register_data", GamesRoot.class);

        Info info = gameService.delGame(response,token).log().all()
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("Game successfully deleted", info.getMessage());
    }

    @Test
    public void updateGameField() {

        UserRoot user = getTestUser();

        gameService.regUser(user)
                .statusCode(201);

        String token = getToken(user.getLogin(), user.getPass());
        Assertions.assertNotNull(token);

        GamesRoot response = gameService.addGameToUser(token,GameGenerator.generateGameFullData())
                .statusCode(201)
                .extract().response().jsonPath().getObject("register_data", GamesRoot.class);

        Info info = gameService.updateGameField(response,token).log().all()
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("New value edited successfully on field company", info.getMessage());

    }


}