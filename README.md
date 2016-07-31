# Quakes

This was initially a proof-of-concept to send me some notifications about nearby earthquakes that I might care about, but I added some additional things because they seemed like they'd be useful:
 * Connects to an Owntracks MQTT broker to pick up location updates so that "nearby" earthquakes are determined by the location data sent by my phone
 * This sends pushover notifications to my phone
 * Connects to the Multnomah county bridge lift API to get updates about the bridges across the Willamette River in Portland, Oregon

Requirements
---
1. Location updates
  * Owntracks on your phone (http://owntracks.org/)
  * An MQTT broker you can access from wherever you run this application 
1. Pushover notifications (https://pushover.net/)
  * Pushover account
  * Pushover user token
  * Define a pushover application
1. Multnomah County bridge lift API key. 
  * Request access at https://multco.us/it/webform/request-access-bridges-public-api 
  * Documentation available at https://api.multco.us/bridges/docs 
1. Slack integration
  * Team name
  * API token
  * Channel name
1. Philips Hue integration
  * Bridge address
  * Username (you'll need to generate this separately)
  * Name of the application (this should go away)
  * Name of the device (this should go away)

How to start the Quakes application
---

1. Run `mvn clean install` to build the application
1. Run `mvn clean package` to package the jar
1. Edit the quakes.yml file or add environment variables to provide the application configuration
1. Start application with `java -jar target/quakes-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

  There is no web interface available yet so this will return a 404

Health Check
---

To see application health enter url `http://localhost:8081/healthcheck`

Behavior while running
---

* Earthquakes within the configured "WORRY" threshold will generate a notification
* All earthquakes and bridge lift events will generate a log message for future reference.
* Hue integration starts at application startup
* Slack integration starts at application startup


Hue-Slack integration
---

Trigger Hue integration by starting a message with "light" or "lights"
 * lights [list|on|off]
   * list: list the lights that the Hue bridge can currently reach
   * on: turn on all the lights reachable from the Hue bridge
   * off: turn off all the lights reachable from the Hue bridge
 * light name, [on|off]
   * on: turn on the named light
   * off: turn off the named light

To Do
---

* Send notification to subscribers
  * Currently it only sends notifications to one destination (me!) because that's all I need
* Additional notification channels
 * Email?
 * Home automation lighting?
* Prune out-of-date location and earthquake data instead of persisting them forever
