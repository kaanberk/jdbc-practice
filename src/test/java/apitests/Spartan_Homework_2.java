package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Spartan_Homework_2 {

    @BeforeClass
    public void setUpClass(){
        RestAssured.baseURI = ConfigurationReader.get("spartanapi.uri");

    }
/*  Q1:
    Given accept type is json
    And path param id is 20
    When user sends a get request to "/spartans/{id}"
    Then status code is 200
    And content-type is "application/json;char"
    And response header contains Date
    And Transfer-Encoding is chunked
    And response payload values match the following:
    id is 20,
    name is "Lothario",
    gender is "Male",
    phone is 7551551687
    */
    @Test
    public void q1(){
        Response response = given().accept(ContentType.JSON).and().pathParam("id", 20).when().get("/spartans/{id}");
        //Then status code is 200
        assertEquals(response.statusCode(),200);
        //And content-type is "application/json;char" -- set=UTF-8
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        //And response header contains Date
        assertTrue(response.getHeaders().hasHeaderWithName("Date"));
        //And Transfer-Encoding is chunked
        assertTrue(response.getHeader("Transfer-Encoding").equals("chunked"));

        JsonPath jsonPath = response.jsonPath();
        //id is 20
        int id = jsonPath.getInt("id");
        assertEquals(id,20);
        //name is "Lothario"
        assertEquals(jsonPath.getString("name"),"Lothario");
        //gender is "Male"
        assertEquals(jsonPath.getString("gender"),"Male");
        //phone is 7551551687
        long phone = jsonPath.getLong("phone");
        assertEquals(phone,7551551687l);
    }
    /*Q2:
    Given accept type is json
    And query param gender = Female
    And queary param nameContains = r
    When user sends a get request to "/spartans/search"
    Then status code is 200
    And content-type is "application/json;char"
    And all genders are Female
    And all names contains r
    And size is 20
    And totalPages is 1
    And sorted is false
*/
    @Test
    public void q2(){
        Response response = given().accept(ContentType.JSON).and().queryParam("gender", "Female")
                .and().queryParam("nameContains", "r")
                .when().get("/spartans/search");
        //Then status code is 200
        assertEquals(response.statusCode(),200);
        //And content-type is "application/json;char" -- set=UTF-8
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        JsonPath jsonPath = response.jsonPath();
        //And all genders are Female
        List<String> gender = jsonPath.getList("content.gender");
        for (String gen : gender) {
            assertEquals(gen,"Female");
        }
        //And all names contains r
        List<String> names = jsonPath.getList("content.name");
        for (String name : names) {
            assertTrue(name.contains("r") || name.startsWith("R"));
        }
        //And size is 20
        int size = jsonPath.getInt("size");
        assertEquals(size,20);
        //And totalPages is 1
        assertEquals(jsonPath.getInt("totalPages"),1);
        //And sorted is false
        boolean sorted = jsonPath.getBoolean("sort.sorted");
        assertEquals(sorted,false);
    }
}
