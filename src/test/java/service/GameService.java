package service;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.games.DlcsItem;
import models.games.GamesRoot;
import models.games.SimilarDlc;
import models.games.UpdateFieldRequest;
import models.users.Info;
import models.users.JwtAuthData;
import models.users.UserRoot;
import utils.GameGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class GameService {
    private DlcsItem createNewDls() {
         Faker faker = new Faker();

        Random random = new Random();
        int randomNumber = Math.abs(random.nextInt());

        SimilarDlc similarDlc = SimilarDlc.builder()
                .dlcNameFromAnotherGame(faker.funnyName().name())
                .isFree(true)
                .build();

        return DlcsItem.builder()
                .dlcName("Gdsfsdf" + randomNumber)
                .isDlcFree(false)
                .similarDlc(similarDlc)
                .price(1223)
                .description(faker.name().name())
                .build();
    }

    public ValidatableResponse auth(UserRoot user) {

        JwtAuthData authData = new JwtAuthData(user.getLogin(), user.getPass());

        return given().contentType(ContentType.JSON)
                .body(authData)
                .post("/login")
                .then();
    }

    public ValidatableResponse regUser(UserRoot user) {

        return given().contentType(ContentType.JSON)
                .body(user)
                .post("/signup")
                .then();
    }

    public ValidatableResponse getGame(String token) {
        return given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .get("/user/games")
                .then();
    }
    public ValidatableResponse getGameId(GamesRoot response,String token) {



        return given().contentType(ContentType.JSON)

                .auth().oauth2(token)
                .pathParam("id", response.getGameId())
                .get("/user/games/{id}")
                .then();
    }

    public ValidatableResponse delGame(GamesRoot response,String token) {
        return given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(response.getGameId())
                .pathParam("id", response.getGameId())
                .delete("/user/games/{id}").then();

    }
public ValidatableResponse updateGameField(GamesRoot response,String token) {

    UpdateFieldRequest request = UpdateFieldRequest.builder()
            .fieldName("company")
            .value("sdsdsdsd")
            .build();
    return given().contentType(ContentType.JSON)
            .auth().oauth2(token)
            .body(request)
            .pathParam("id", response.getGameId())
            .put("/user/games/{id}/updateField").then();
}
public ValidatableResponse deleteDlc(GamesRoot response,String token){
   return given().contentType(ContentType.JSON)
            .auth().oauth2(token)
            .body(GameGenerator.generateGameFullData().getDlcs())
            .pathParam("id", response.getGameId())
            .delete("/user/games/{id}/dlc").then();
}
public ValidatableResponse udpateDlcInfo(GamesRoot response,String token){
    List<DlcsItem> getNewDlc = new ArrayList<>();
    getNewDlc.add(createNewDls());

    return given().contentType(ContentType.JSON)
            .auth().oauth2(token)
            .body(getNewDlc)
            .pathParam("id", response.getGameId())
            .put("/user/games/{id}")
            .then();
}
}




