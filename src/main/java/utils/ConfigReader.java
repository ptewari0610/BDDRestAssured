package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static ConfigReader configReader;


    /** Parameterized constructor for reading Properties file
     @return properties This returns properties object
     */
    private ConfigReader() {
        properties = new Properties();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/Config.properties"));
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Creating instance of ConfigLoader class
     */
    public static ConfigReader getInstance() {
        if (configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    /** Fetching value from properties file
     @key This is the key whose value need to be retrieved
     @return value
     */
    public String fetchValueFromConfig(String key) {
        String prop = properties.getProperty(key);
        try{
            if (prop != null){
                return prop;
            }
            else
                throw new UserDefinedException("Key is not specified in properties file");
        }catch(UserDefinedException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
