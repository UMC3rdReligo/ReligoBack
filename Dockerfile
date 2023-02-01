FROM eclipse-temurin:11 AS builder
WORKDIR /gradle
COPY gradlew build.gradle settings.gradle ./
COPY gradle/ gradle/
RUN ./gradlew build -x test --parallel --continue 2> /dev/null || true
COPY . .
RUN ["./gradlew", "clean", "build", "--no-daemon"]

FROM eclipse-temurin:11
EXPOSE 8080
WORKDIR /religo
COPY --from=builder /gradle/build/libs/*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]
