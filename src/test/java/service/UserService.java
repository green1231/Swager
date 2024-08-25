package service;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.users.Info;
import models.users.JwtAuthData;
import models.users.UserRoot;
import utils.GameGenerator;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static io.restassured.RestAssured.given;

public class UserService {

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

    public ValidatableResponse getUser(String token) {

        return given().auth().oauth2(token)
                .get("/user")
                .then();
    }

    public ValidatableResponse delUser(String token) {
        return given().auth().oauth2(token)
                .delete("/user")
                .then();
    }

    public ValidatableResponse updUserPass(String NewPassword, String token) {

        Map<String, String> pass = new HashMap<>();
        pass.put("password", NewPassword);

        return given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(pass)
                .put("/user")
                .then();
    }

    public ValidatableResponse getToken(JwtAuthData authData) {
        return given().contentType(ContentType.JSON)
                .body(authData)
                .post("/login")
                .then();
    }



}
