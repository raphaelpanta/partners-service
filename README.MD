# Partners Service

A REST API service to manager partners. This service could create partners with their location and coverage area, find by their id and locate the nearest partner covered for a given location.

# How to Run service locally

It uses `docker` and `docker-compose` in order to run the api service and database locally.
How to install: 
- [docker](https://docs.docker.com/engine/install/)
- [docker-compose](https://docs.docker.com/compose/install/)

## Run 

In order to run, use the follow command to run:
```code:shell
docker-compose up
```
It will build the application image on the first run and run a mongo db container.
Application exposes port `7000` and mongo db at `27017` so make sure those ports are available before running.
An instance of mongo dvb are required before running application.

## Alternative ways to running application

OpenJDK 14 and kotlin 1.4 are required to build and run application without containers. 

In root path of project:

To build:
```code:shell
./gradlew build shadowJar
```

To run:
```code:shell
export DB_HOST=<mongo db host ip or address>
export DB_PORT=<mongo db port>
java -jar build/libs/*-all.jar 
```

Run a mongo instance for developing and testing:
```code:shell
docker run --name mongo-test -p 27017:27017 -d mongo:4.4.1
```

Or install [mongodb](https://docs.mongodb.com/manual/installation/)

# Deploy

To build image for deployment run the following:

```
docker run partners-service:1.0.0
```

To create a service container image.

# API documentation

Application will be available under `http://localhost:7000/`.

>> Open API Doc is available under http://localhost:7000/api-docs

>> Swagger interface is available under http://localhost:7000/swagger-ui


