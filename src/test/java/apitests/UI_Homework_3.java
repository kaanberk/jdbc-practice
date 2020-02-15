package apitests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.testng.Assert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;


public class UI_Homework_3 {

    @BeforeClass
    public void setUpClass(){
        RestAssured.baseURI = ConfigurationReader.get("uinames.uri");
    }
/*No params test
1. Send a get request without providing any parameters
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that name, surname, gender, region fields have value
*/
    @Test
    public void noParamTest(){
        //1. Send a get request without providing any parameters
        Response response = when().get();
        //2. Verify status code 200, content type application/json; charset=utf-8
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        //3. Verify that name, surname, gender, region fields have value
        assertFalse(response.path("name").toString().isBlank());
        assertFalse(response.path("surname").toString().isBlank());
        assertFalse(response.path("gender").toString().isBlank());
        assertFalse(response.path("region").toString().isBlank());
    }
    /*
Gender test
1. Create a request by providing query parameter: gender, male or female
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that value of gender field is same from step 1
     */
    @Test
    public void genderTest(){
        //1. Create a request by providing query parameter: gender, male or female
        Response response = given().queryParam("gender", "male").and().when().get();
        //2. Verify status code 200, content type application/json; charset=utf-8
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        //3. Verify that value of gender field is same from step 1
        JsonPath jsonPath= response.jsonPath();
        assertEquals(jsonPath.getString("gender"),"male");
    }
/*
2 params test
1. Create a request by providing query parameters: a valid region and gender
NOTE: Available region values are given in the documentation
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that value of gender field is same from step 1
4. Verify that value of region field is same from step 1
 */
    @Test
    public void twoParamsTest(){
        given().queryParam("region","Germany").and()
                .queryParam("gender","male")
                .when().get("https://uinames.com/api")
                .then().statusCode(200).and().contentType("application/json; charset=utf-8")
                .and().body("region",equalTo("Germany"),"gender",equalTo("male"));
    }

    /*
Invalid gender test
1. Create a request by providing query parameter: invalid gender
2. Verify status code 400 and status line contains Bad Request
3. Verify that value of error field is Invalid gender
     */
    @Test
    public void invalidGenderTest(){
        given().queryParam("gender","invalidGender").when().get("https://uinames.com/api")
                .then().statusCode(400)
                .and().assertThat().statusLine(containsString("Bad Request"))
                .and().body("error",equalTo("Invalid gender"));
    }
    /*
    Invalid region test
1. Create a request by providing query parameter: invalid region
2. Verify status code 400 and status line contains Bad Request
3. Verify that value of error field is Region or language not found
     */
    @Test
    public void invalidRegionTest(){
        given().queryParam("region","regionValid").when().get("https://uinames.com/api")
                .then().statusCode(400).and().assertThat().statusLine(containsString("Bad Request"))
                .and().body("error",equalTo("Region or language not found"));
    }
    /*
    Amount and regions test
1. Create request by providing query parameters: a valid region and amount (must be bigger than 1)
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that all objects have different name+surname combination
     */
    @Test
    public void amountAndRegionTest(){
        Response response = given().queryParam("region", "Spain").and().queryParam("amount", 2)
                .when().get();
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        String name1 = response.getBody().path("name[0]") + " " + response.path("surname[0]");
        String name2 = response.getBody().path("name[1]") + " " + response.path("surname[1]");
        assertFalse(name1.equals(name2));
    }
    /*
    3 params test
1. Create a request by providing query parameters: a valid region, gender and amount (must be bigger than 1)
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that all objects the response have the same region and gender passed in step 1
     */
    @Test
    public void threeParamsTest(){
        given().queryParam("region","Spain").and()
                .queryParam("gender","male").and()
                .queryParam("amount",2).and()
                .when().get("https://uinames.com/api").then()
                .statusCode(200).and().contentType("application/json; charset=utf-8")
                .and().body("region",hasItems("Spain"),"gender",hasItems("male"));
    }
    /*
    Amount count test
1. Create a request by providing query parameter: amount (must be bigger than 1)
2. Verify status code 200, content type application/json; charset=utf-8
3. Verify that number of objects returned in the response is same as the amount passed in step 1
     */
    @Test
    public void amountCountTest(){
        Response response = given().queryParam("amount", 2).and()
                .when().get("?amount=2");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        List<String> names = response.getBody().path("name");
        assertTrue(names.size()==2);
    }
}
