# Quakes

This was initially a proof-of-concept to send me some notifications about nearby earthquakes that I might care about, but I added some additional things because they seemed like they'd be useful:
 * Connects to an Owntracks MQTT broker to pick up location updates so that "nearby" earthquakes are determined by the location data sent by my phone

How to start the Quakes application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/quakes-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


To Do
---

* Get additional admin-specified "interesting" locations and their boundaries
 * Home location
 * Nearby volcanoes
* Calculate if there are any new earthquakes close enough to the locations we are monitoring
* Save new earthquakes
* Send notification to subscribers
 * Pushover API
 * Slack messages
