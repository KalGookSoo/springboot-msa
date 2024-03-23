# Start with a base image containing Java runtime (OpenJDK 17)
FROM openjdk:17-jdk-alpine

# Add Maintainer Info
LABEL maintainer="ga.miro3721@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/user-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} my-application.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/my-application.jar"]