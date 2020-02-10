package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTest {

    String spartanAllUrl = "http://3.82.231.156:8000/api/spartans";

    @Test
    public void viewAllSpartans(){
        Response response = RestAssured.get(spartanAllUrl);
        //print the status code
        System.out.println("response.statusCode() = " + response.statusCode());
        //print response body
        System.out.println("response.body().asString() = " + response.body().asString());
        //pretty body print -- better view
        System.out.println("response.body().prettyPrint() = " + response.body().prettyPrint());
    }

    /*  When user send get request to /api/spartans end point
        Then status code must be 200
        And body should contains "Orion"
     */

    @Test
    public void viewSpartanTest1(){
        Response response = RestAssured.get(spartanAllUrl);
        //Then status code must be 200
        Assert.assertEquals(response.statusCode(),200);
        //And body should contains "Orion"
        Assert.assertTrue(response.body().asString().contains("Orion"));
    }

    /*  Given accept type application/json
        When user send GET request to /api/spartans end point
        Then status code should be 200
        And response Content type must be Jason
        And body should contains "Orion"
     */

    @Test
    public void viewSpartanTest2(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(spartanAllUrl);
        //Then status code should be 200
        Assert.assertEquals(response.statusCode(),200);
        //And response Content type must be Jason
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");
    }

    /*  Given accept type application/xml
        When user send GET request to /api/spartans end point
        Then status code should be 200
        And response Content type must be xml
        And body should contains "Orion"
     */
    @Test
    public void viewSpartanTest3(){
        Response response = RestAssured.given().accept(ContentType.XML).when().get(spartanAllUrl);
        //Then status code should be 200
        Assert.assertEquals(response.getStatusCode(),200);
        //And response Content type must be XML
        Assert.assertEquals(response.contentType(),"application/xml;charset=UTF-8");
        //And body should contains "Orion"
        Assert.assertTrue(response.body().asString().contains("Orion"));
    }

    /*  Given accept type application/xml
        When user send GET request to /api/spartans end point
        Then status code should be 200
        And response Content type must be xml
        And body should contains "Orion"
     */

    @Test
    public void viewSpartanTest4(){
            //request starts here
            given().accept(ContentType.XML)
              .when().get(spartanAllUrl)
                //response starts here
              .then().statusCode(200)
              .and().contentType("application/xml;charset=UTF-8");
    }

 /*     Given the accept type Json
        When I send get request to /api/spartans/3
        Then status code must be 200
        And Content type should be json
        and body should contains Fidole
     */

    @Test
    public void viewSpartanTest5(){
        Response response = given().accept(ContentType.JSON).when().get(spartanAllUrl);

        assertEquals(response.getStatusCode(),200);

        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        assertTrue(response.body().asString().contains("Fidole"));
    }

    //TASK
    /*
        Create a new class HRApiTests
        create a @Test getALLRegionsTest
        send a get request to AllRegions API endpoint
        -print status code
        -print content type
        -pretty print response JSON
        verify that status code is 200
        verify that content type is "application/json"
        verify that json response body contains "Americas"
        verify that json response body contains "Europe"
        *try to use static imports for both RestAssured and testng
        *store response inside the Response type variable
     */
  /*
        Given the accept type XML
        When I send get request to /api/spartans/3
        Then status code must be 406
     */

}
