# Quakes

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

* Get feed from http://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php every 5 minutes
* Get location data from owntracks
* Get admin-specified "interesting" locations and their boundaries
* Calculate if there are any new earthquakes close enough to the locations we are monitoring
* Save new earthquakes
* Send notification to subscribers
