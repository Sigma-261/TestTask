package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.printf("avg: %f", avg());
    }

    static double avg(){

        System.out.println("The function for finding the average number is called");
        try {

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("C:\\testData.json"));

            // convert JSON array to list of data from json file
            List<JsonInfo> testData = new Gson().fromJson(reader, new TypeToken<List<JsonInfo>>() {}.getType());
            if(testData == null){
                System.out.println("File is empty!");
                return 0;
            }
            // close reader
            reader.close();

            //return avg value
            return testData.stream()
                    .filter(c -> c.ups_adv_battery_run_time_remaining != 0)
                    .mapToDouble(d -> d.ups_adv_battery_run_time_remaining)
                    .average()
                    .orElse(0.0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    static void max(){

        System.out.println("max");
    }

    static void values(){

        System.out.println("values");
    }
}
