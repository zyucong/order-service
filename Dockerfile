FROM openjdk:8 as base
LABEL maintainer="zhuyingcong"
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY start.sh ./
RUN ./mvnw dependency:resolve
RUN ./start.sh
COPY src ./src

FROM base as development
CMD ["./mvnw", "spring-boot:run"]

FROM base as build
RUN ./mvnw package

FROM base as production
EXPOSE 8080
COPY --from=build /app/target/orders-*.jar /orders.jar
CMD ["java", "-jar", "/orders.jar"]
