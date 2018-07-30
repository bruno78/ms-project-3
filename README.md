# Wifi Hotspot using NYC Open Data

This application discovers Wifi hotspots near you by using [NYC OpenData](https://opendata.cityofnewyork.us/). Users can search by typing the region or zipcode and save the favorite to the list.

## Technical requirements

* NYC Wifi Hotspot locations [API](https://data.cityofnewyork.us/City-Government/NYC-Wi-Fi-Hotspot-Locations/yjub-udmw)
* Spring Boot
* Spring Cloud
    * Eureka Service Discovery and Registry
    * Zuul Gateway Service
* PostGreSQL
* React as frontend UI

### Instructions

To use the application, download it on your server. Start by typing `docker-compose up` and when the application is running, start the front end UI by typing `yarn start`.

### Tests

* There are two feature tests for full CRUD each (users and favorites). They passed!
* There are 22 controllers unit tests for users and 22 controllers tests for favorites. They passed!

### About this project

This the 3rd project as exercise for General Assembly's SECA (Software Engineering Accelerator) program.

The students had to develop a Microservice application using Spring Boot, Spring Cloud, and React and use NYC OpenData as data source.

They also have to write a case study to present and explain why the use of Microservice in this case.

The case study can be found [here](https://github.com/bruno78/ms-project-3/blob/master/CASE_STUDY.md)