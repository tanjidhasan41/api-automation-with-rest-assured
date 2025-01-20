package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import setup.UserModel;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {

    Properties properties;

    public UserController(Properties properties) {
        this.properties = properties;
        RestAssured.baseURI = "https://dailyfinanceapi.roadtocareer.net";
    }

    public Response login(UserModel userModel) {

        Response response = given().contentType("application/json")
                .body(userModel).when().post("/api/auth/login");
        return response;

    }

    public Response addItem(UserModel userModel) {

        Response response = given().contentType("application/json")
                .body(userModel)
                .header("Authorization", "Bearer " + properties.getProperty("userToken"))
                .when().post("api/costs");
        return response;

    }

    public Response getItemList() {

        Response response = given().contentType("application/json")
                .header("Authorization", "Bearer " + properties.getProperty("userToken"))
                .when().get("api/costs");
        return response;

    }

    public Response editItemName(UserModel userModel) {

        Response response = given().contentType("application/json")
                .body(userModel)
                .header("Authorization", "Bearer " + properties.getProperty("userToken"))
                .when().put("api/costs/" + properties.getProperty("itemID"));
        return response;

    }

    public Response deleteItem() {

        Response response = given().contentType("application/json")
                .header("Authorization", "Bearer " + properties.getProperty("userToken"))
                .when().delete("api/costs/" + properties.getProperty("itemID"));
        return response;

    }

}
