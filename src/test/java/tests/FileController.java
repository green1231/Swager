package tests;

import models.Info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FileController {

    @Test

    public void dowloadImage(){

        byte[] bytes = given().get("http://85.192.34.140:8080/api/files/download")
          .then().log().all().extract().asByteArray();

        Assertions.assertNotNull(bytes);
        Assertions.assertTrue(bytes.length > 0);

        File file = new File("src/test/resources/threadqa.jpeg");
        Assertions.assertEquals(file.length(),bytes.length);

    }

    @Test
    public void uploadFile(){

       Info info =  given().multiPart(new File("src/test/resources/threadqa.jpeg"))
                .when().post("http://85.192.34.140:8080/api/files/upload")
                .then().log().all()
                .statusCode(200)
                .extract().jsonPath().getObject("info", Info.class);;

        Assertions.assertEquals("success", info.getStatus());
        Assertions.assertEquals("file uploaded to server", info.getMessage());


    }

}
