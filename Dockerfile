FROM gradle:6.6.1-jdk14 as build
ENV ENV=CONTAINER
ENV GRADLE_USER_HOME=/home/gradle/cache
VOLUME $GRADLE_USER_HOME
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build shadowJar --no-daemon

FROM adoptopenjdk/openjdk14:jdk-14.0.2_12

EXPOSE 7000

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/partners-service.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app/partners-service.jar"]
