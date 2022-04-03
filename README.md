# Getting Started

Project is spring-boot based. Build tool is maven.

To build it please execute below command.

```
mvn clean install
```

To run it from command line please navigate to `projectDirectory/target` directory and execute below command

```
java -jar demo-0.0.1-SNAPSHOT.jar
```

By default, web server will start on port `8080`

#Testing

Application expose two spring rest controllers. First is responsible for room management.
Second should be used to manage reservations.

In first step we need to configure number of available rooms. 
To do that we should call `room` endpoint with required configuration

Example:

```
curl --location --request POST 'localhost:8080/room?premiumRoomCount=10&economyRoomCount=12'
```

As result, we will get list of all configured rooms.

To add new room reservation `reservation` controller should be used.
To create rooms in batch using array price we may use `/batch` endpoint in reservation controller.

Example:

```
curl --location --request POST 'localhost:8080/reservation/batch' \
--header 'Content-Type: application/json' \
--data-raw '["23", "45", "155", "374", "22", "99.99", "100", "101", "115", "209"]'
```

As result, we will receive summary of daily income with list of all active reservations