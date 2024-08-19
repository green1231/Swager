package tests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.cars.ResponseItem;
import models.keys.NumbersPow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
public class ResponseTrainTests {



    @Test
    public void getCarBrands(){

      List<ResponseItem> carsBrand = given()
              .contentType(ContentType.JSON)
              .get("http://85.192.34.140:8080/api/easy/carBrands")
              .then().log().all()
              .extract().body().jsonPath().getList("", ResponseItem.class);

      String bmwBrand = "bmw";
//ищем в списке бренд bmw
        boolean isBmwExist = carsBrand.stream()
                .anyMatch(x -> x.getBrand().equalsIgnoreCase(bmwBrand));
//проверяем
        Assertions.assertTrue(isBmwExist);

//список бмв машин X серии
        List<String> xSeries = carsBrand.stream()
                .filter(x -> x.getBrand().equalsIgnoreCase(bmwBrand))
                .findAny().orElseThrow(() -> new AssertionError("Машин " + bmwBrand + " нет в списке"))
                .getModels().stream()
                .filter(x -> x.startsWith("X")).toList();

        Assertions.assertEquals(xSeries.size(), 5);

        String carBrand = "Mercedes-Benz";

        long carsCount = carsBrand.stream()
                .filter(x -> x.getBrand().equalsIgnoreCase(carBrand))
                .findAny().orElseThrow(() -> new AssertionError("Машин " + carBrand + " нет в списке"))
                .getModels().size();

        Assertions.assertEquals(carsCount, 58);

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

       Response response  = given()
               .redirects()
               .follow(false)
               .when()
               .get("http://85.192.34.140:8080/api/easy/redirect")
               .then().log().all()
               .extract().response();
       String location = response.getHeader("Location");
       int statusCode = response.getStatusCode();

       Assertions.assertEquals(301, statusCode);
       Assertions.assertEquals("https://www.youtube.com/@net_vlador", location);


}

@Test
    public void getVariationsKeyForTrainingRetrievingResponses(){

    NumbersPow numbersPow = given().contentType(ContentType.JSON)
                .get("http://85.192.34.140:8080/api/easy/nums")
                .then().log().all()
                .extract().response().jsonPath().getObject("numbersPow.nums", NumbersPow.class);
    System.out.println(numbersPow);




}

}
