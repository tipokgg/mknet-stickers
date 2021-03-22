FROM openjdk:11
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} stickers-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/stickers-0.0.1-SNAPSHOT.war"]