package HarryPotterApi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class withKeyTests {
    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = ConfigurationReader.get("harrypotter.uri");
        String apiKey = "$2a$10$gBNXWJI.lARc2mkBVQe9b.sJeo05Xd63CMkt6Op/r/Cch1X6mYwAa";
    }
/**
    Verify number of characters
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify response contains 194 characters
 */
    @Test
    public void VerifyNumOfChars(){

        Response response = given().accept(ContentType.JSON).and().queryParam("key","$2a$10$gBNXWJI.lARc2mkBVQe9b.sJeo05Xd63CMkt6Op/r/Cch1X6mYwAa")
                .when().get("/characters");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
    }
    /**
    Verify number of character id and house
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify all characters in the response have id field which is not empty
    4. Verify that value type of the field dumbledoresArmy is a boolean in all characters in the response
    5. Verify value of the house in all characters in the response is one of the following:
       "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */
    @Test
    public void VerifyNumId(){

    }

}
