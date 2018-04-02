# Microservice Project 3

Big Green Acme NYC Tours has been on the market since 1985. It started small but the company grew fast since they were providing high quality services, leaving their customers very happy. Now, they want to expand to the next level. They want to know all the free services that NYC has to offer, making sure their travelers come to the city and can take advantage of it. They want a want to become resource for travelers. One big issue that their customers face is the lack of phone services. Sometimes phone services from abroad can be very expensive. That's when the agency came with the idea to implement an application that can track all the wifi hotspots in the city. They believe if their travelers can use their services to track free wifi while in town, that definitely will increase their business and customer satisfaction. 

## Solution 

A microservice application that will start using NYC Open data to track all the wifi hotspots in the city. 

The reason for using a microservice is very simple. This application will have huge traffic of data and customers. It can be easily scaled by adding other apis, like restaurants and hotel with recommendations. If a service needs to be repaired, replaced, or updated it can easily be done without disrupting the others. 

## Case Study

Flyway Travel Agency started using microservices as they expanded. 

> _"The company continued to expand over time, adding on not just a bookings process, but also a payment process that included advanced features like compensation (rolling back bookings), integration of traditional web services in service tasks and a credit card fraud detection system."_


### Technical requirements

* NYC Open DATA to get all the City's data on Wifi HotSpots
* Spring Cloud that includes Netflix Technologies such:
    * Zuul Api Gateway to manage incoming web traffic
    * NetFlix's Eureka Service Registry to manage Api's discoverability 
* Spring client service for NYC Wifi Data
* Spring client service for Users
* Spring client service for Booking
* Spring client service for Transactions 
* React User interface 

### Instructions 

To use the application, download it on your server. Start by typing `docker-compose up` and when the application is running, start the front end by typing `yarn start`. 
