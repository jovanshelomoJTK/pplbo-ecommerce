# Menggunakan image Java 17 sebagai base image
FROM openjdk:17-jdk-slim AS build

# Menyalin file build.gradle dan src ke dalam kontainer
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle
COPY src /app/src

# Beralih ke direktori aplikasi
WORKDIR /app

# Menjalankan gradle wrapper untuk membangun aplikasi
RUN ./gradlew clean build -x test

# Menggunakan image alpine sebagai base image untuk container final
FROM openjdk:17-jdk-alpine

# Menyalin hasil build dari tahap sebelumnya ke dalam container
COPY --from=build /app/build/libs/*.jar /app/application.jar

# Perintah untuk menjalankan aplikasi saat container dijalankan
ENTRYPOINT ["java","-jar","/app/application.jar"]
