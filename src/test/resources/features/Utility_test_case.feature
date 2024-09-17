@API
Feature: Geo Location Utility

  As a user of the Open Weather Map
  I want to retrieve location data for US cities and zip codes
  So that I can get accurate latitude, longitude, and other information

  Scenario: Retrieve location data for a valid US zip code
    Given the Geo Location Utility is set up with a valid API key
    When I request location data for zip code
      | 90210 |
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    And I should receive the following location data:
      | Name      | Beverly Hills |
      | Latitude  | 34.0901       |
      | Longitude | -118.4065     |
      | Country   | US            |
      | ZIP       | 90210         |

  Scenario: Retrieve location data for a valid US city and state
    Given the Geo Location Utility is set up with a valid API key
    When I request location data for
      | Madison, WI |
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    Then I should receive the following data for a location
      | Name      | Madison   |
      | Latitude  | 43.07476  |
      | Longitude | -89.38376 |
      | Country   | US        |
      | State     | Wisconsin |

  Scenario: Retrieve location data for multiple locations
    Given the Geo Location Utility is set up with a valid API key
    When I request location data for the following locations:
      | Chicago, IL |
      | 10001       |
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    Then I should receive location data for "Chicago" in "Illinois"
    And I should receive location data for zip code "10001" in "New York"

  Scenario: Attempt to retrieve location data for invalid locations
    Given the Geo Location Utility is set up with a valid API key
    When I request location data for
      | 00000   |
    Then status code should be 404
    And I should receive an error message for invalid location


  Scenario: Attempt to run the utility without providing any locations
    Given the Geo Location Utility is set up with a valid API key
    When I run the utility without providing any locations
    Then status code should be 404
    And I should receive a message asking to provide at least one location