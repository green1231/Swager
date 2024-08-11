package tests;

import io.restassured.http.ContentType;
import models.Info;
import models.JwtAuthData;
import models.UserRoot;
import org.example.Main;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class SimplTest {

    private static Random random;

    @Test  // В логах указанно это - "games": []?


    public void positiveRegistration(){
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


    }
@Test
    public void positiveAdminAuth(){

    JwtAuthData authData = new JwtAuthData("admin", "admin");

    String token = given().contentType(ContentType.JSON)
            .body(authData)
            .post("http://85.192.34.140:8080/api/login")
            .then().log().all()
            .statusCode(200)
            .extract().jsonPath().getString("token");

    }
    @Test
    public void positiveNewUserAuth(){
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

    }
    @Test
    public void positiveGetUserInfo(){

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

        given().auth().oauth2(token)
                .get("http://85.192.34.140:8080/api/user")
                .then().log().all()
                .statusCode(200);


}

@Test
    public void  UpdatingUserPassword(){

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

    Map<String,String> password = new HashMap<>();
    password.put("password", "234234234");

    given().contentType(ContentType.JSON)
            .auth().oauth2(token)
            .body(password)
            .put("http://85.192.34.140:8080/api/user")
            .then().log().all()
            .statusCode(200);
    }



@Test
    public void DeletUser(){

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

        given().auth().oauth2(token)
                .delete("http://85.192.34.140:8080/api/user")
                .then().log().all()
                .statusCode(200);


    }

}
