FROM maven:3.8.4-ibm-semeru-17-focal as maven
WORKDIR /usr/src/app
COPY . .
RUN mvn -B clean package

FROM openjdk:16
WORKDIR /parser
COPY --from=maven target/CronParser*-exec.jar ./CronParser.jar
CMD ["java", "-jar", "./CronParser.jar"]