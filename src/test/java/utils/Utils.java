package utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

    public static void setEnvironmentVariable(String key, String value) throws ConfigurationException {

        PropertiesConfiguration configuration = new PropertiesConfiguration("./src/test/resources/config.properties");
        configuration.setProperty(key, value);
        configuration.save();

    }

    public static int generateRandomID(int min, int max) {

        double randomID = Math.random()*(max-min)+min;
        return (int) randomID;

    }

}
