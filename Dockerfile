FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt/app
ENV DATASOURCE_URL=${DATASOURCE_URL} \
    DATASOURCE_USER=${DATASOURCE_USER} \
    DATASOURCE_PASSWORD=${DATASOURCE_PASSWORD}
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
ENV DATASOURCE_URL=${DATASOURCE_URL} \
    DATASOURCE_USER=${DATASOURCE_USER} \
    DATASOURCE_PASSWORD=${DATASOURCE_PASSWORD}
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]
