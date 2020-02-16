package Day6_gson;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class POJO_deserialize {

    @Test
    public void oneSpartanWithPojo(){
        Response response = given()
                .accept(ContentType.JSON).pathParam("id",15)
                .when().get("http://3.82.231.156:8000/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        //JSON to My Custom Class (POJO)
        //deserialize json to pojo java object
        //taking response and converting to Spartan object
        Spartan spartan15 = response.body().as(Spartan.class);
        System.out.println(spartan15.toString());

        System.out.println("spartan15.getId() = " + spartan15.getId());
        System.out.println("spartan15.getName() = " + spartan15.getName());
        System.out.println("spartan15.getGender() = " + spartan15.getGender());
        System.out.println("spartan15.getPhone() = " + spartan15.getPhone());

        assertEquals(spartan15.getId(),15);
        assertEquals(spartan15.getName(),"Meta");
        assertEquals(spartan15.getGender(),"Female");
        assertEquals(spartan15.getPhone(),new Long(1938695106));
    }
    @Test
    public void regionWithPojo(){
        //request
        Response response = get("http://3.82.231.156:1000/ords/hr/regions");
        assertEquals(response.statusCode(),200);

        //JSON to Region Cass
        //Deserizalition
        Region regions= response.body().as(Region.class);

        System.out.println(regions.getCount());

        List<Item> regionList = regions.getItems();
        System.out.println(regionList.get(0).getRegionName());
        System.out.println("regionList.get(1).getRegionId() = " + regionList.get(1).getRegionId());

        System.out.println(regions.getItems().get(0).getRegionName());

        for (Item item : regionList) {
            System.out.println(item.getRegionName());
        }
    }
    @Test
    public void GsonExample(){
        //creating gson object
        Gson gson = new Gson();

        //De-Serialize and serialize with gson object
        //Deseriailze -->JSON TO Java Object

        //------------------------DESERIALIZATION----------------------
        String myjson = "{\n" +
                "    \"id\": 15,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";
        //converting json to pojo(Spartan class)
        Spartan spartan15 = gson.fromJson(myjson,Spartan.class);

        System.out.println("spartan15.getName() = " + spartan15.getName());
        System.out.println("spartan15.getPhone() = " + spartan15.getPhone());
        //------------------------SERIALIZATION----------------------
        //jAVA OBJECT TO JSON
        Spartan spartanEU  = new Spartan(10,"Mike","Male",571943543421l);
        //it will take spartan eu information and convert to json
        String jsonSpartanEU = gson.toJson(spartanEU);

        System.out.println("jsonSpartanEU = " + jsonSpartanEU);
    }

}
