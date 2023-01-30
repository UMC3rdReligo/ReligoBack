FROM eclipse-temurin:11 AS builder
WORKDIR /gradle
COPY . .
RUN ["./gradlew", "clean", "build", "--no-daemon"]

FROM eclipse-temurin:11
EXPOSE 8080
WORKDIR /religo
COPY --from=builder /gradle/build/libs/*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]
