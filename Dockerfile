FROM maven:sapmachine AS backend-builder

WORKDIR /usr/src/app/backend
COPY backend/ ./

FROM openjdk:21

WORKDIR /usr/src/app/backend
COPY --from=backend-builder /usr/src/app/backend/target/backend-0.0.1-SNAPSHOT.jar ./

EXPOSE 8080

CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]
