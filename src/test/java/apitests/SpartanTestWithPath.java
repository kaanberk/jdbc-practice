package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class SpartanTestWithPath {

    @BeforeClass
    public void setUpClass(){
        RestAssured.baseURI = ConfigurationReader.get("spartanapi.uri");

    }


    /*
    Given accept type is json
    And path param id is 10
    When user sends a get request to "/spartans/{id}"
    Then status code is 200
    And content-type is "application/json;char"
    And response payload values match the following:
        id is 10,
        name is "Lorenza",
        gender is "Female",
        phone is 3312820936
 */

    @Test
    public void getSpartansWithPath(){
        //request
        Response response = given().accept(ContentType.JSON).and()
                .pathParam("id", 10)
                .when().get("/spartans/{id}");

        //verify content type and status code
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        //print response json body
        System.out.println("ID: "+response.body().path("id").toString());
        System.out.println("Name: "+response.path("name").toString());
        System.out.println("Gender: "+response.path("gender").toString());
        System.out.println("Phone: "+ response.path("phone").toString());

        //save them
        int id = response.path("id");
        String firstName = response.path("name");
        String SpartanGender = response.path("gender");
        long phoneNumber  = response.path("phone");

        //do assertions
        assertEquals(id,10);
        assertEquals(firstName,"Lorenza");
        assertEquals(SpartanGender,"Female");
        assertEquals(phoneNumber,3312820936L);
    }

    @Test
    public void getAllSpartansWithPath(){
        //request
        Response response = get("/spartans/");

        assertEquals(response.statusCode(),200);
        //print the first id
        int firstId = response.path("id[0]");
        System.out.println("FirstId = "+firstId);

        //print first name from the all spartans
        String firstname = response.path("name[0]");
        System.out.println("First name = "+firstname);

        //print last name from the all spartans
        String lastname = response.path("name[-1]");
        System.out.println("Last name = " + lastname);

        //get all firstnames and print out
        List<String> allNames = response.path("name");
        System.out.println("Names size = " + allNames.size());
        System.out.println("All Names = " + allNames);

        //print all phone number one by one
        List<Object> allPhoneNo = response.path("phone");

        for (Object no : allPhoneNo) {
            System.out.println(no);
        }
    }
}


