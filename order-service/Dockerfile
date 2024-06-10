FROM gradle:8.7-jdk17-alpine AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle /home/gradle/java-code/
WORKDIR /home/gradle/java-code
RUN gradle clean dependencies --build-cache -x bootJar
RUN ls -la /home/gradle/cache_home

FROM gradle:8.7-jdk17-alpine AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY build.gradle settings.gradle /app/
COPY src /app/src
WORKDIR /app
RUN gradle bootJar

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /app/build/libs/*.jar /app/application.jar
ENTRYPOINT ["java","-jar","/app/application.jar"]