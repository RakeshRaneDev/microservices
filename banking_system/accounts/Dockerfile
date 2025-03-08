#Start with abase image java runtime
FROM openjdk:17-jdk-slim

#Information arround who maintains the image
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="artist.com"

#Add the application jar the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]