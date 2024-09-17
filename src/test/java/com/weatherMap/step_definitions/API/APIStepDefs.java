package com.weatherMap.step_definitions.API;

import com.weatherMap.utilities.API_WeatherMapUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class APIStepDefs {

    Map<String, Map<String, String>> actualLocation;
    String errorMessage;

    @Given("the Geo Location Utility is set up with a valid API key")
    public void setup_GeoLocation_Utility() {
        System.out.println("------------------------------------------------------");
        System.out.println("--API url will set up in next step base on the Input--");
        System.out.println("------------------------------------------------------");
    }

    @When("I request location data for zip code")
    public void request_Location_Data_For_ZipCode(List<String> zipCode) {
        actualLocation = API_WeatherMapUtils.getWeatherMap(zipCode);
        System.out.println(actualLocation);
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int statusCode) {
        assertEquals(statusCode, API_WeatherMapUtils.getStatusCode());

    }

    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        assertEquals(contentType, API_WeatherMapUtils.getContenType());
    }


    @When("I request location data for")
    public void request_Location_Data_For_City_State(List<String> location) {
        actualLocation = API_WeatherMapUtils.getWeatherMap(location);
        System.out.println(actualLocation);
    }

    @When("I request location data for the following locations:")
    public void request_Location_Data_For_Multiple_Locations(List<String> locations) {
        actualLocation = API_WeatherMapUtils.getWeatherMap(locations);
        System.out.println(actualLocation);
    }

    @When("I run the utility without providing any locations")
    public void run_Utility_Without_Locations() {
        try {
            API_WeatherMapUtils.getWeatherMap(Collections.emptyList());
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("I should receive the following location data:")
    public void verify_Location_Data(Map<String, String> expectedlocation) {
        assertFalse("No location data found", actualLocation.isEmpty());

        String zipCode = expectedlocation.get("ZIP");
        Map<String, String> actual = actualLocation.get(zipCode);

        assertNotNull("No data found for ZIP: " + zipCode, actualLocation);

        for (Map.Entry<String, String> entry : expectedlocation.entrySet()) {
            assertEquals("Mismatch for " + entry.getKey(),
                    entry.getValue(),
                    actual.get(entry.getKey()));
        }
    }

    @Then("I should receive the following data for a location")
    public void i_should_receive_the_following_data_for_a_location(Map<String, String> expectedData) {
        assertNotNull("Actual location data is null", actualLocation);
        assertFalse("Actual location data is empty", actualLocation.isEmpty());

        // Assuming actualLocation is a Map<String, Map<String, String>>
        Map<String, String> locationData = actualLocation.values().iterator().next();

        for (Map.Entry<String, String> entry : expectedData.entrySet()) {
            String key = entry.getKey();
            String expectedValue = entry.getValue();
            String actualValue = locationData.get(key);

            assertNotNull("Value for " + key + " is null", actualValue);
            assertEquals("Mismatch for " + key, expectedValue, actualValue);
        }
    }

    @Then("I should receive location data for {string} in {string}")
    public void verify_Location_Data_For_City_State(String city, String state) {
        String key = city + ", " + API_WeatherMapUtils.getStateAbbreviation(state);
        Map<String, String> locationData = actualLocation.get(key);
        assertNotNull("No data found for " + city + ", " + state, locationData);
        assertEquals(city, locationData.get("Name"));
        assertEquals(state, locationData.get("State"));

    }

    @Then("I should receive location data for zip code {string} in {string}")
    public void verify_Location_Data_For_ZipCode(String zipCode, String city) {
        Map<String, String> locationData = actualLocation.get(zipCode);
        assertNotNull("No data found for zip code " + zipCode, locationData);
        assertEquals(city, locationData.get("Name"));
        assertEquals(zipCode, locationData.get("ZIP"));
    }

    @Then("I should receive an error message for invalid location")
    public void verify_Error_Message_For_Invalid_Location() {
        assertNotNull("Actual location data is null", actualLocation);
        for (Map.Entry<String, Map<String, String>> entry : actualLocation.entrySet()) {
            assertTrue("Expected empty map for invalid location: " + entry.getKey(),
                    entry.getValue().isEmpty());
        }
        System.out.println("------------------------------------------------------");
        System.err.println("--------Invalid location, please double check----------");
        System.out.println("------------------------------------------------------");
    }

    @Then("I should receive a message asking to provide at least one location")
    public void verify_Message_For_No_Locations() {
        assertEquals("Input list must not be null or empty", errorMessage);
        System.out.println("------------------------------------------------------");
        System.err.println("--------Please provide at least one location----------");
        System.out.println("------------------------------------------------------");
    }


}
