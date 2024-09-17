### After cloning the project from GitHub:
- In the top-right corner of IntelliJ, locate the run configuration dropdown between the "Run with Coverage" and "Run" buttons.
- Click on the dropdown and select "Edit Configurations..." at the bottom of the list.
- click the "+" button and choose "JUnit" from the list.
- Name the new configuration "CukeRunner".
- In the configuration form, find the "Environment variables" section.
- Click the folder icon next to the Environment variables field to open the Environment Variables dialog.
Add the following variables:
Name: URL, Value: https://openweathermap.org/api/geocoding-api
Name: BASE_URL, Value: http://api.openweathermap.org/geo/1.0
Name: YOUR_API_KEY, Value: [Insert your actual API key here]
- Click "OK" to close the Environment Variables dialog, then click "OK" again to save the run configuration.

---You're now ready to run the tests---
You can either:
Find the CukeRunner class in the project structure and run it directly, or
Select "CukeRunner" from the run configuration dropdown and click the green "Run" button next to it.
This setup ensures that your tests will run with the correct environment variables and API key.