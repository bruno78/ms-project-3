
# Overview

New York City Search Engine

The City of New York has a data api they would like to add a front-end to. `The API`
handles the ability to search for keywords or limit results you will be making
requests to this `API` and displaying it's results. On top of a front-end The City
would also like the ability to save results after a user signs up (does not need to authenticate).

This is the current site, use this as your jump off point:
https://a856-cityrecord.nyc.gov/

![SECA](/prompt.png)


API Docs:

- https://dev.socrata.com/foundry/data.cityofnewyork.us/buex-bi6w
- https://dev.socrata.com/

API endpoints

- https://data.cityofnewyork.us/resource/buex-bi6w.json
- https://data.cityofnewyork.us/City-Government/City-Record-Online/dg92-zbpx/data


# Requirements

Your proposal must include (using this template):
- A clear problem statement from the client
- What business problem are you trying to solve with technology?
- Clearly present technical requirements of solving the business problem
- A solution for a monolithic existing service service
- Explain how to breakdown the monolithic service
- How to make the monolithic service scalable
- The pros/cons of implementing a monolithic problem
- How the microservice will solve the companies problems
- Include an external case study that demonstrates a similar problem/solution

Overall, your app must:
- Consist of two repositories: one for the back-end, one for the front-end
- Have appropriately commented code
- Adhere to the provided style guide for writing your Java (look at Google)
- Contain feature, integration, and unit tests on both the front-end and back-end using the appropriate libraries.

### Back-end:

- Use Spring with microservices
- Create a Database to save, update, and delete records.

### Testing

- Write End-to-End feature tests that use the full `docker-compose` environment
- Write Spring controller tests
- Write Repository tests
- Write tests for an endpoint that test for functionality, status codes, and error handling (Invalid params/ids, missing routes)
- Run all tests using Gradle tasks
- Use Selenide to write fluent feature tests


### Front-end:

- Be a SPA built in React
- Communicate with your back-end API in order to retrieve favorites
- CSS grid/flexbox
- Use at least one media query
- Use components for reusable elements

# Bonuses

- Include the ability to access data with API keys using some form of auth.


# Deliverables

- Deck of proposed service for a client
- Case studies that matched the problem statement
- full-stack app using data provided
- A git repository hosted on GitHub