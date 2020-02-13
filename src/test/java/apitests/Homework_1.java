package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Homework_1 {
    @BeforeClass
    public void setUpClass(){
        RestAssured.baseURI = ConfigurationReader.get("hrapi.uri");
    }
/*
    Q1:
        - Given accept type is Json
        - Path param value- US
        - When users sends request to /countries
        - Then status code is 200
        - And Content - Type is Json
        - And country_id is US
        - And Country_name is United States of America
        - And Region_id is 2
    */
    @Test
    public void q1(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("q","{\"country_id\":\"US\"}").when().get("/countries");
        //Then status code is 200
        assertEquals(response.statusCode(),200);
        //And Content - Type is Json
        assertEquals(response.contentType(),"application/json");

        String country_id = response.path("items.country_id[0]");
        String country_name = response.path("items.country_name[0]");
        int region_id = response.path("items.region_id[0]");

        //And country_id is US
        assertEquals(country_id,"US");
        //And Country_name is United States of America
        assertEquals(country_name,"United States of America");
        //And Region_id is 2
        assertEquals(region_id,2);
    }
/*
    Q2:
        - Given accept type is Json
        - Query param value - q={"department_id":80}
        - When users sends request to /employees
        - Then status code is 200
        - And Content - Type is Json
        - And all job_ids start with 'SA'
        - And all department_ids are 80
        - Count is 25
*/

    @Test
    public void q2(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("q","{\"department_id\":80}").and().get("/employees");
        //Then status code is 200
        assertEquals(response.statusCode(),200);
        //And Content - Type is Json
        assertEquals(response.contentType(),"application/json");

        List<String> job_id = response.path("items.job_id");
        //And all job_ids start with 'SA'
        for (String id : job_id) {
            assertTrue(id.startsWith("SA"));
        }
        List<Integer> department_id = response.path("items.department_id");
        //And all department_ids are 80
        for (int id : department_id) {
            assertEquals(id,80);
        }
        //Count is 25
        int count = response.body().path("count");
        assertEquals(count,25);
    }
/*
    Q3:
        - Given accept type is Json
        -Query param value q= region_id 3
        - When users sends request to /countries
        - Then status code is 200
        - And all regions_id is 3
        - And count is 6
        - And hasMore is false
        - And Country_name are;
        Australia,China,India,Japan,Malaysia,Singapore
    */
    @Test
    public void q3(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("q","{\"region_id\":3}").when().get("/countries");
        //Then status code is 200
        assertEquals(response.statusCode(),200);
        //And all regions_id is 3
        List<Object> region_id = response.path("items.region_id");
        for (Object id : region_id) {
            assertEquals(id,3);
        }
        //And count is 6
        int count = response.body().path("count");
        assertEquals(count,6);
        //And hasMore is false
        boolean hasMore = response.path("hasMore");
        assertEquals(hasMore,false);
        /* And Country_name are;
           Australia,China,India,Japan,Malaysia,Singapore
        */
        List<String> expectedCountries = new ArrayList<String>();

        expectedCountries.add("Australia");
        expectedCountries.add("China");
        expectedCountries.add("India");
        expectedCountries.add("Japan");
        expectedCountries.add("Malaysia");
        expectedCountries.add("Singapore");

        List<String> actualCountries = response.path("items.country_name");

        assertEquals(actualCountries,expectedCountries);

    }
}

