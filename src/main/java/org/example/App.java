package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class App 
{
    public static void main( String[] args )
    {
        switch (args[0]) {
            case "avg":
                System.out.printf("avg: %f", avg(args[1]));
                break;
            case "max":
                System.out.printf("max: %d", max(args[1]));
                break;
            case "values":
                System.out.printf("values: %s", values(args[1]));
                break;
            default:
                System.out.print("invalid command!");
                break;
        }
    }

    static List<JsonInfo> readTestData(String pathTest) {
        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(pathTest));

            // convert JSON array to list of data from json file
            List<JsonInfo> testData = new Gson().fromJson(reader, new TypeToken<List<JsonInfo>>() {}.getType());
            if(testData == null){
                System.out.println("File is empty!");
                return new ArrayList<>();
            }
            // close reader
            reader.close();
            return testData;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    static double avg(String pathTest){
        System.out.println("\nThe function for finding the average in [ups_adv_battery_run_time_remaining] is called");

        //return avg value
        return readTestData(pathTest).stream()
                    .filter(c -> c.ups_adv_battery_run_time_remaining != 0)
                    .mapToInt(d -> d.ups_adv_battery_run_time_remaining)
                    .average()
                    .orElse(0.0);
    }

    static int max(String pathTest){
        System.out.println("\nThe function for finding the max in [ups_adv_output_voltage] is called");

        //return max value
        return readTestData(pathTest)
                    .stream()
                    .filter(c -> c.ups_adv_output_voltage != 0)
                    .mapToInt(v -> v.ups_adv_output_voltage)
                    .max().orElseThrow(NoSuchElementException::new);
    }

    static List<String> values(String pathTest){
        System.out.println("\nThe function for finding unique value [host] is called");

        //return unique value
        return readTestData(pathTest).stream()
                    .filter(c -> c.host != null)
                    .map(v -> v.host)
                    .distinct()
                    .collect(Collectors.toList());

    }
}
