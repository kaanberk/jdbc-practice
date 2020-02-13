package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class HRApiTestsWithPath {
    @BeforeClass
    public void setUpClass(){
        RestAssured.baseURI = ConfigurationReader.get("hrapi.uri");
    }

    @Test
    public void getCountriesWithPath(){
        Response response = given().queryParam("q", "{\"region_id\":2}").when().get("/countries");
        assertEquals(response.statusCode(),200);

        //count value
        System.out.println(response.path("count").toString());

        /*
            items.country_id[0] --> AR
            items.country_name[1]   -->Brazil
            items.links[2].href --> "http://54.161.128.36:1000/ords/hr/countries/CA"
            items.country_name --> list with all countries names
            */

        String firstCountryId = response.path("items.country_id[0]");
        String secondCountryName = response.path("item.country_name[1]");

        System.out.println("firstCountryId = " + firstCountryId);
        System.out.println("secondCountryName = " + secondCountryName);

        String link2 = response.path("items.links[2].href[0]");
        System.out.println("link2 = " + link2);

        //get all countries
        List<String> allCountries = response.path("items.country_name");
        System.out.println("allCountries = " + allCountries);

        //assert that all regions id`s are equal to 2
        List<String>allRegions=response.path("items.region_id");
        for (Object r:allRegions){
            Assert.assertTrue(r.equals(2));
        }
    }

    @Test
    public void getCountriesWithPath2(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("IT_PROG"));

        //assert that all job_id s are IT_PROG
        List<Object> allJobId = response.body().path("items.job_id");
        for (Object o : allJobId) {
            Assert.assertTrue(o.equals("IT_PROG"));
        }
    }
}
