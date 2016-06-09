# Quakes

This was initially a proof-of-concept to send me some notifications about nearby earthquakes that I might care about, but I added some additional things because they seemed like they'd be useful:
 * Connects to an Owntracks MQTT broker to pick up location updates so that "nearby" earthquakes are determined by the location data sent by my phone
   * This sends pushover notifications
 * Connects to the Multnomah county bridge lift API to get updates about the bridges across the Willamette River in Portland, Oregon

Requirements
---
1. Location updates
  * Owntracks on your phone
  * An MQTT broker you can access from wherever you run this application 
2. Pushover notifications (https://pushover.net/)
  * Pushover account
  * Pushover user token
  * Define a pushover application
3. Multnomah County bridge lift API key. 
  * Request access at https://multco.us/it/webform/request-access-bridges-public-api 
  * Documentation available at https://api.multco.us/bridges/docs 

How to start the Quakes application
---

1. Run `mvn clean install` to build the application
2. Run `mvn clean package` to package the jar
3. Edit the quakes.yml file or add environment variables to provide the application configuration
4. Start application with `java -jar target/quakes-1.0-SNAPSHOT.jar server config.yml`
5. To check that your application is running enter url `http://localhost:8080`
6. Earthquakes within the configured "WORRY" threshold will generate a notification
7. All earthquakes and bridge lift events will generate a log message for future reference.

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


To Do
---

* Get additional admin-specified "interesting" locations and their boundaries
 * Home location
 * Nearby volcanoes
* Send notification to subscribers
  * Currently it only sends notifications to one destination (me!) because that's all I need
* Additional notification channels
 * Slack messages
 * Email?
* Prune out-of-date location and earthquake data instead of persisting them forever
