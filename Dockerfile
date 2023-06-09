FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt/app
ARG REDIS_HOST
ARG REDIS_PORT
ARG DATASOURCE_URL
ARG DATASOURCE_USER
ARG DATASOURCE_PASSWORD
ENV REDIS_HOST=${REDIS_HOST} \
    REDIS_PORT=${REDIS_PORT} \
    DATASOURCE_URL=${DATASOURCE_URL} \
    DATASOURCE_USER=${DATASOURCE_USER} \
    DATASOURCE_PASSWORD=${DATASOURCE_PASSWORD}
RUN echo $DATASOURCE_URL
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
ARG REDIS_HOST
ARG REDIS_PORT
ARG DATASOURCE_URL
ARG DATASOURCE_USER
ARG DATASOURCE_PASSWORD
ENV REDIS_HOST=${REDIS_HOST} \
    REDIS_PORT=${REDIS_PORT} \
    DATASOURCE_URL=${DATASOURCE_URL} \
    DATASOURCE_USER=${DATASOURCE_USER} \
    DATASOURCE_PASSWORD=${DATASOURCE_PASSWORD}
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]
