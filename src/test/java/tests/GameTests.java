package tests;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import models.games.DlcsItem;
import models.games.GamesRoot;
import models.games.SimilarDlc;
import models.users.Info;
import models.users.JwtAuthData;
import models.users.UserRoot;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.GameGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.parsing.Parser.JSON;


public class GameTests {
    private static final Faker faker = new Faker();
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

        GamesRoot response = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(GameGenerator.generateGameFullData())
                .post("http://85.192.34.140:8080/api/user/games")
                .then().log().all()
                .extract().response().jsonPath().getObject("register_data", GamesRoot.class);

       int statusCode = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .get("http://85.192.34.140:8080/api/user/games/"+response.getGameId())
                .then().log().all()
                .extract().statusCode();
        Assertions.assertEquals(200, statusCode);

    }
@Test
    public void updateGameDlcInfo(){

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

        GamesRoot response = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(GameGenerator.generateGameFullData())
                .post("http://85.192.34.140:8080/api/user/games")
                .then().log().all()
                .extract().response().jsonPath().getObject("register_data", GamesRoot.class);

        SimilarDlc similarDlc = SimilarDlc.builder()
            .dlcNameFromAnotherGame(faker.funnyName().name())
            .isFree(true)
            .build();

        DlcsItem dlcsItem = DlcsItem.builder()
            .dlcName("Gdsfsdf"+randomNamber)
            .isDlcFree(false)
            .similarDlc(similarDlc)
            .price(1223)
            .description(faker.name().name())
            .build();

        List<DlcsItem> getNewDlc = new ArrayList<>();
        getNewDlc.add(dlcsItem);

        Info info = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(getNewDlc)
                .put("http://85.192.34.140:8080/api/user/games/"+response.getGameId())
                .then().log().all()
               .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus()) ;
        Assertions.assertEquals("DlC successfully changed", info.getMessage()) ;;

    }
    @Test
    public void deleteDlc(){

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

        GamesRoot response = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(GameGenerator.generateGameFullData())
                .post("http://85.192.34.140:8080/api/user/games")
                .then().log().all()
                .extract().response().jsonPath().getObject("register_data", GamesRoot.class);

        Info info = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(GameGenerator.generateGameFullData().getDlcs())
                .delete("http://85.192.34.140:8080/api/user/games/"+response.getGameId()+"/dlc")
                .then().log().all()
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus()) ;
        Assertions.assertEquals("Game DLC successfully deleted", info.getMessage()) ;;

    }
    @Test
    public void deleteGame (){
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

        GamesRoot response = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(GameGenerator.generateGameFullData())
                .post("http://85.192.34.140:8080/api/user/games")
                .then().log().all()
                .extract().response().jsonPath().getObject("register_data", GamesRoot.class);

        Info info = given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(response.getGameId())
                .delete("http://85.192.34.140:8080/api/user/games/"+response.getGameId())
                .then().log().all()
                .extract().jsonPath().getObject("info", Info.class);

        Assertions.assertEquals("success", info.getStatus()) ;
        Assertions.assertEquals("Game successfully deleted", info.getMessage()) ;;

}

}





