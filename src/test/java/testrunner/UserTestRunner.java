package testrunner;

import com.github.javafaker.Faker;
import controller.AdminController;
import controller.UserController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;
import setup.UserModel;
import utils.Utils;

public class UserTestRunner extends Setup {

    @Test(priority = 1, description = "User can login with valid creds")
    public void login() throws ConfigurationException, InterruptedException {

        UserController userController = new UserController(properties);
        UserModel userModel = new UserModel();

        userModel.setEmail(properties.getProperty("userEmail"));
        userModel.setPassword("1234");
        Response response = userController.login(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String userToken = jsonPath.get("token");
        System.out.println(userToken);
        Utils.setEnvironmentVariable("userToken", userToken);

    }

    @Test(priority = 2, description = "User can add items")
    public void addItem() throws ConfigurationException {

        UserController userController = new UserController(properties);
        UserModel userModel = new UserModel();

        userModel.setItemName("Laptop");
        userModel.setQuantity(1);
        userModel.setAmount(Utils.generateRandomID(3000, 8000) + "0");
        userModel.setPurchaseDate("2025-01-19");
        userModel.setMonth("January");
        userModel.setRemarks("Lenovo");

        Response response = userController.addItem(userModel);
        System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String itemID = jsonPath.get("_id");
        String amount = jsonPath.get("amount");
        System.out.println(itemID);
        System.out.println(amount);
        Utils.setEnvironmentVariable("itemID", itemID);
        Utils.setEnvironmentVariable("amount", amount);

    }

    @Test(priority = 3, description = "User can check item list")
    public void getItemList() {

        UserController userController = new UserController(properties);
        Response response = userController.getItemList();
        System.out.println(response.asString());

    }

    @Test(priority = 4, description = "User can edit item name")
    public void editItemName() {

        UserController userController = new UserController(properties);
        UserModel userModel = new UserModel();

        userModel.setItemName("Gaming Laptop");
        userModel.setQuantity(1);
        userModel.setAmount(properties.getProperty("amount"));
        userModel.setPurchaseDate("2025-01-19");
        userModel.setMonth("January");
        userModel.setRemarks("Lenovo");

        Response response = userController.editItemName(userModel);
        System.out.println(response.asString());

    }

    @Test(priority = 5, description = "User can delete item")
    public void deleteItem() throws InterruptedException {

        UserController userController = new UserController(properties);
        Response response = userController.deleteItem();
        System.out.println(response.asString());

        Thread.sleep(3000);

        JsonPath jsonPath = response.jsonPath();
        String messageActual = jsonPath.get("message");
        Assert.assertTrue(messageActual.contains("Cost deleted successfully"));

    }

}
