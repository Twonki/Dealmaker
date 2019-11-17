##### Stage 1
##### Build and Run tests

FROM maven:3.6.1-jdk-12 as builder

WORKDIR /app

# Copy project files to the docker image
COPY . .

RUN mvn package verify

#### STAGE 2
#### Deploying the application

FROM tomcat:9-jdk11-openjdk-slim

RUN mkdir -p /usr/db/

COPY --from=builder /app/dealMaker.db /usr/db/

COPY --from=builder /app/target/dealmakerapi-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

#ENTRYPOINT ["java -jar start.jar"]
