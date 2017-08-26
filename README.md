# simple-json-web-service
Implementation of a simple web service emulating a simple betting service.

## Details
This application is a web service, it uses json as its data exchange format, is based on spring mvc/spring boot and emulates a betting service.

## Simple requirements
The service should:
- show available events to place bets on
- place bets
- cancel bets
- settle a bet, 2 mins after it has been placed. Win or lose can be random. The odds will be fixed: e.g. 2x
- show actively placed bets
- show cancelled bets
- show settled bets
- show a history of placed bets along with their settlement details
- support only "Home / away: who wins" bet types
- support a single user (i.e. anyone hitting the endpoints shall be considered as being the same person)
- comply with richardson maturity model (rmm) level 2
- support only GET, POST and DELETE
- document the endpoints using swagger

## Out of scope
- authorisation
- testing (unit, integration, system and acceptance) because it really does not have any business logic
- hateoas (rmm level 3)
- security

## Design decisions
- the by-feature code organisation was used
- although the [hexagonal architecture](http://alistair.cockburn.us/Hexagonal+architecture) was used, ports and adapters were not put in different packages, due to the small number of code files

# How to use
- build the app and run it
```
mvn clean verify; ./target/simple-json-web-service-1.0.jar
```
- the app will start to listen to port 2000, on localhost (you can change that from the application.yml file)
- go to the swagger ui page and use the endpoints:
```
http://localhost:2000/swagger-ui.html
```