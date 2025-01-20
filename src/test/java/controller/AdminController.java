package controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import setup.UpdateUserModel;
import setup.UserModel;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class AdminController {

    Properties properties;

    public AdminController(Properties properties) {
        this.properties = properties;
        RestAssured.baseURI = "https://dailyfinanceapi.roadtocareer.net";
    }

    public Response login(UserModel userModel) {

        Response response = given().contentType("application/json")
                .body(userModel).when().post("/api/auth/login");
        return response;

    }

    public Response userList() {

        Response response = given().contentType("application/json")
                .header("Authorization", "Bearer " + properties.getProperty("token"))
                .when().get("/api/user/users");

        return response;

    }

    public Response searchUser(String userID) {

        Response response = given().contentType("application/json")
                .header("Authorization", "Bearer " + properties.getProperty("token"))
                .when().get("/api/user/" + userID);

        return response;

    }

    public Response updateUser(UpdateUserModel userModel) {

        Response response = given().contentType("application/json")
                .body(userModel)
                .header("Authorization", "Bearer " + properties.getProperty("token"))
                .when().put("api/user/" + properties.getProperty("userID"));

        return response;

    }

}
