FROM maven:3.9-eclipse-temurin-21 AS build

ENV MAVEN_OPTS="-Xmx1024m"
WORKDIR /app
COPY backend ./backend
COPY frontend ./frontend

RUN cd backend && mvn -B clean install -DskipTests
WORKDIR /app/frontend
RUN mvn -B -Pproduction clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/frontend/target/*.war app.war
ADD https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/11.0.21/jetty-runner-11.0.21.jar jetty-runner.jar

EXPOSE 8080
CMD ["java", "-jar", "jetty-runner.jar", "--port", "8080", "--path", "/", "app.war"]