## Estágio de Build
#FROM gradle:8.5-jdk21 AS build
#WORKDIR /app
#COPY . .
#RUN ./gradlew bootJar --no-daemon
#
## Estágio de Runtime
#FROM eclipse-temurin:21-jre-jammy
#WORKDIR /app
#COPY --from=build /app/build/libs/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
