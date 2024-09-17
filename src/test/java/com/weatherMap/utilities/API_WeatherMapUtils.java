package com.weatherMap.utilities;

import com.github.javafaker.Faker;
import com.weatherMap.pojos.location.LocationByZipcode;
import com.weatherMap.pojos.location.LocationInfor;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.*;

import static io.restassured.RestAssured.given;

public class API_WeatherMapUtils {
    /**
     * -- GETTER --
     * This method returns the status code
     * no parameters
     */
    @Getter
    private static int statusCode;
    /**
     * -- GETTER --
     * This method returns the response contenType
     * no parameters
     */
    @Getter
    private static String contenType;

    public static Map<String, Map<String, String>> getWeatherMap(List<String> dataInput) {
        if (dataInput == null || dataInput.isEmpty()) {
            throw new IllegalArgumentException("Input list must not be null or empty");
        }
        Map<String, Map<String, String>> results = new LinkedHashMap<>();

        for (String each : dataInput) {
            if (each == null || each.trim().isEmpty()) {
                continue;
            }

            Map<String, String> locationData;
            if (each.matches("\\d{5}")) {
                locationData = zipcodeAndCountryCodeInput(each);
            } else {
                locationData = cityStateCountryInput(each);
            }

            if (locationData != null) {
                results.put(each, locationData);
            }
        }

        return results.isEmpty() ? null : results;

    }

    /**
     * this method retrieve data from API and store it as a map
     *
     * @param input is a string
     * @return
     */
    private static Map<String, String> cityStateCountryInput(String input) {
        Map<String, String> info = new LinkedHashMap<>();
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input must be provided");
        }

        String[] parts = input.split(",");
        String city = parts[0].trim();
        String state = (parts.length > 1) ? parts[1].trim() : "";
        String country = (parts.length > 2) ? parts[2].trim() : "";

        StringBuilder queryBuilder = new StringBuilder(city);

        if (!state.isEmpty()) {
            queryBuilder.append(", ").append(state);
        }

        if (!country.isEmpty()) {
            queryBuilder.append(", ").append(country);
        }

        String query = queryBuilder.toString();

        String url = String.format("%s/direct?q=%s, US&appid=%s",
                System.getenv("BASE_URL"), query,System.getenv("YOUR_API_KEY"));
        System.out.println(url);


        Response response = getResponse(url);
        if (response.getStatusCode() != 200 || response.body().equals("[]")) {
            return new HashMap<>();
        }
        JsonPath jsonPath = response.jsonPath();
        String responseBody = response.getBody().asString();
        if (responseBody.equals("[]")) {
            System.out.println("No data found for: " + input);
            return Collections.emptyMap();
        }
        // Deserialize into a List of LocationInfor objects
        List<LocationInfor> locations = jsonPath.getList("", LocationInfor.class);

        if (locations.isEmpty()) {
            System.out.println("No data found for: " + input);
            return Collections.emptyMap();
        }

        // Use the first location in the list
        LocationInfor locationInfor = locations.get(0);

        String name = locationInfor.getName();
        Float lat = locationInfor.getLat();
        Float lon = locationInfor.getLon();
        String countryOutPut = locationInfor.getCountry();
        String stateOutPut = locationInfor.getState();

        info.put("Name", name);
        info.put("Latitude", lat.toString());
        info.put("Longitude", lon.toString());
        info.put("Country", countryOutPut);
        info.put("State", stateOutPut);
        System.out.println(info);
        return info;


    }

    /**
     * this method retrieve data from API and store it as a map
     *
     * @param zipcode is a string
     * @return
     */

    private static Map<String, String> zipcodeAndCountryCodeInput(String zipcode) {
        Map<String, String> info = new LinkedHashMap<>();
        String url = String.format("%s/zip?zip=%s,US&appid=%s",
                System.getenv("BASE_URL"), zipcode,System.getenv("YOUR_API_KEY"));

        Response response = getResponse(url);
        if (response.getStatusCode() != 200) {
            return new HashMap<>();
        } else if (response.getStatusCode() == 200) {
            JsonPath jsonPath = response.jsonPath();

            //Deserialize to LocationByZipcode class
            LocationByZipcode locationByZipcode = jsonPath.getObject("", LocationByZipcode.class);
            String name = locationByZipcode.getName();
            Float lat = locationByZipcode.getLat();
            Float lon = locationByZipcode.getLon();
            String country = locationByZipcode.getCountry();
            String zip = locationByZipcode.getZip();

            info.put("Name", name);
            info.put("Latitude", lat.toString());
            info.put("Longitude", lon.toString());
            info.put("Country", country);
            info.put("ZIP", zip);
        } else {
            // Handle non-200 status codes
            info.put("error", "API request failed with status code: " + response.getStatusCode());
        }
        System.out.println(info);
        return info;
    }

    /**
     * this method send request and return response
     * @param url
     * @return
     */
    private static Response getResponse(String url) {
        Response response = given().accept(ContentType.JSON)
                .when().get(url);
        statusCode = response.statusCode();
        contenType = response.getContentType();
        return response;
    }

    /**
     * this method change the State name to ISO abbreviation code as 2 letters format
     * @param stateName
     * @return
     */
    public static String getStateAbbreviation(String stateName) {
        Map<String, String> stateAbbreviations = new HashMap<>();
        stateAbbreviations.put("Illinois", "IL");
        // Add more state abbreviations as needed
        return stateAbbreviations.getOrDefault(stateName, stateName);
    }


    /**
     * this method is using to provide a fake data
     *
     * @return fake data as Map<String, String>
     */
    public static Map<String, Object> getLocationMap() {

        Faker faker = new Faker();
        Map<String, Object> locationMap = new LinkedHashMap<>();
        locationMap.put("name", faker.address().cityName());
        locationMap.put("country", faker.country());
        locationMap.put("state", faker.address().state());
        locationMap.put("zip", faker.address().zipCode());
        locationMap.put("countryCode", faker.address().countryCode());


        return locationMap;
    }
}
