package HarryPotterApi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.equalTo;

public class noKeyTests {

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = ConfigurationReader.get("harrypotter.uri");
    }
/**
    Verify sorting hat
    1. Send a get request to /sortingHat. Request includes :
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that response body contains one of the following houses:
          "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
    */

    @Test
    public void VerifySortingHat(){
        Response response =given().accept(ContentType.JSON).and().get("/sortingHat");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        String house = response.body().asString();

        String[] aa = {"\"Gryffindor\"","\"Ravenclaw\"","\"Slytherin\"", "\"Hufflepuff\""};

        for (int i =0; i<aa.length;i++){
            boolean asdf = false;
            if ( aa[i].contains(house)){
                asdf = true;
                assertEquals(house,aa[i]);
                break;
            }
        }
    }
/**
    Verify bad key
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value invalid
    2. Verify status code 401, content type application/json; charset=utf-8
    3. Verify response status line include message Unauthorized
    4. Verify that response body says "error": "API Key Not Found"
*/
    @Test
    public void VerifyBadKey(){
        given().accept(ContentType.JSON).and().queryParam("key","incncwncwjc")
                .when().get("/characters")
                .then().statusCode(401).and().contentType("application/json; charset=utf-8").and()
                .statusLine(containsString("Unauthorized")).and()
                .body("error",equalTo("API Key Not Found"));
    }
/**
    Verify no key
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    2. Verify status code 409, content type application/json; charset=utf-8
    3. Verify response status line include message Conflict
    4. Verify that response body says "error": "Must pass API key for request"
 */
    @Test
    public void VerfiyNoKey(){
        given().accept(ContentType.JSON).when().get("/characters").then()
                .statusCode(409).contentType("application/json; charset=utf-8")
                .and().statusLine(containsString("Conflict"))
                .and().body("error",equalTo("Must pass API key for request"));
    }
}
