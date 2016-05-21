Feature: Health Check

	Scenario: UptimeRobot GETs the health check endpoint
		When an HTTP GET on the "/admin/info" endpoint
		Then an "OK" status is returned
		And the content type is "application/json"
		And the application should report a name of "SDKMAN Vendor Announce Service"

	Scenario: UptimeRobot HEADs the health check endpoint
		When an HTTP PUT on the "/admin/info" endpoint
		Then an "METHOD_NOT_ALLOWED" status is returned
