FROM gradle:8.7-jdk17-alpine AS build
COPY build.gradle settings.gradle /app/
COPY src /app/src
WORKDIR /app
RUN gradle clean build -x test --no-daemon --parallel


FROM openjdk:17-jdk-alpine
COPY --from=build /app/build/libs/*.jar /app/application.jar
ENTRYPOINT ["java","-jar","/app/application.jar"]
