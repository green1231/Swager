package tests;

import io.restassured.http.ContentType;


import models.carsBrands.ResponseItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ResponseTrainController {



    @Test
    public void getCarBrands(){

      List<ResponseItem> carsBrand = given()
              .contentType(ContentType.JSON)
              .get("http://85.192.34.140:8080/api/easy/carBrands")
              .then().log().all()
              .extract().body().jsonPath().getList("", ResponseItem.class);

      carsBrand.stream().forEach(x-> Assertions.assertEquals("brand", x.getBrand()));


    }



    @Test
    public void getVersionAPI(){

       String versionAPI = given()
               .contentType(ContentType.JSON)
               .get("http://85.192.34.140:8080/api/easy/version")
               .then().log().all()
               .extract().jsonPath().getString("apiVersion");

        Assertions.assertEquals("1.0.2", versionAPI);
        System.out.println(versionAPI);

    }


@Test
    public void redirectsSpecificAddressReturnsStatusCode301(){

       int statusCode  = given()
               .redirects()
               .follow(false)
               .when()
               .get("http://85.192.34.140:8080/api/easy/redirect")
               .then().log().all()
               .extract().statusCode();

       Assertions.assertEquals(301, statusCode);


}

@Test
    public void getVariationsKeyForTrainingRetrievingResponses(){

        given().contentType(ContentType.JSON)
                .get("http://85.192.34.140:8080/api/easy/nums")
                .then().log().all()
                .statusCode(200);


}

}
