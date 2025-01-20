package testrunner;

import com.github.javafaker.Faker;
import controller.RegistrationController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

import java.io.IOException;
import java.util.Properties;

public class RegistrationTestRunner extends Setup {

    @Test(description = "User can register new account")
    public void registration() throws ConfigurationException, IOException, ParseException {

        RegistrationController registrationController = new RegistrationController(properties);
        UserModel userModel = new UserModel();
        Faker faker = new Faker();

        userModel.setFirstName(faker.name().firstName());
        userModel.setLastName(faker.name().lastName());
        userModel.setEmail(faker.name().firstName().toLowerCase() + "@gmail.com");
        userModel.setPassword("1234");
        userModel.setPhoneNumber("0170" + Utils.generateRandomID(1000000, 9999999));
        userModel.setAddress("Alaska");
        userModel.setGender("Male");
        userModel.setTermsAccepted(true);

        Response response = registrationController.register(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String userID = jsonPath.get("_id");
        String lastName = jsonPath.get("lastName");
        String userEmail = jsonPath.getString("email");

        Utils.setEnvironmentVariable("userID", userID);
        Utils.setEnvironmentVariable("lastName", lastName);
        Utils.setEnvironmentVariable("userEmail", userEmail);

    }

}
