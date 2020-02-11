package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class HRApiTests {

    //TASK
    /*  Create a new class HRApiTests
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

    String regionsURL = "http://3.82.231.156:1000/ords/hr/regions";

    @Test
    public void getAllRegionsTest(){
        //send a get request to AllRegions API endpoint
        Response response = RestAssured.get(regionsURL);
        //-print status code
        System.out.println("response.statusCode() = " + response.statusCode());
        //-print content type
        System.out.println("response.contentType() = " + response.contentType());
        //-pretty print response JSON
        System.out.println("response.prettyPrint() = " + response.prettyPrint());

        //verify that status code is 200
        assertEquals(response.statusCode(),200);
        //verify that content type is "application/json"
        assertEquals(response.contentType(),"application/json");
        //verify that json response body contains "Americas"
        assertTrue(response.prettyPrint().contains("Americas"));
        //verify that json response body contains "Europe"
        assertTrue(response.prettyPrint().contains("Europe"));
    }

}
