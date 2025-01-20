package testrunner;

import com.github.javafaker.Faker;
import controller.AdminController;
import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;
import setup.UpdateUserModel;
import setup.UserModel;
import utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AdminTestRunner extends Setup {

    @Test(priority = 1, description = "Admin can login using valid creds")
    public void login() throws ConfigurationException, InterruptedException {

        AdminController adminController = new AdminController(properties);
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin123");
        Response response = adminController.login(userModel);
        //System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String token = jsonPath.get("token");
        System.out.println(token);
        Utils.setEnvironmentVariable("token", token);

    }

    @Test(priority = 2, description = "Admin can check all users list")
    public void userList() {

        AdminController adminController = new AdminController(properties);
        Response response = adminController.userList();
        System.out.println(response.asString());

    }

    @Test(priority = 3, description = "Admin can search user by user id")
    public void searchUser() {

        AdminController adminController = new AdminController(properties);
        Response response = adminController.searchUser(properties.getProperty("userID"));
        System.out.println(response.asString());

    }

    @Test(priority = 4, description = "Admin can update user info")
    public void updateUser() throws IOException, ParseException, InterruptedException {

        AdminController adminController = new AdminController(properties);
        UpdateUserModel updateUserModel = new UpdateUserModel();
        Faker faker = new Faker();

        updateUserModel.setFirstName(faker.name().firstName());
        updateUserModel.setLastName(properties.getProperty("lastName"));
        updateUserModel.setEmail(properties.getProperty("userEmail"));
        updateUserModel.setPhoneNumber("0170" + Utils.generateRandomID(1000000, 9999999));
        updateUserModel.setAddress("Alaska");
        updateUserModel.setGender("Male");
        updateUserModel.setTermsAccepted(true);

        Response response = adminController.updateUser(updateUserModel);
        System.out.println(response.asString());

    }

}
