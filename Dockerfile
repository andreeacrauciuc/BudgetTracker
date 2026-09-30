FROM maven:3.9-eclipse-temurin-21

ENV MAVEN_OPTS="-Xmx1024m"

WORKDIR /app
COPY backend ./backend
COPY frontend ./frontend

RUN cd backend && mvn -B clean install -DskipTests

WORKDIR /app/frontend
RUN mvn -B -Pproduction clean package -DskipTests

EXPOSE 8080

CMD ["mvn", "-Pproduction", "jetty:run-war"]