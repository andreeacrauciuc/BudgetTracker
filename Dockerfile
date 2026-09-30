FROM maven:3.9-eclipse-temurin-21

WORKDIR /app
COPY backend ./backend
COPY frontend ./frontend

RUN cd backend && mvn -B clean install -DskipTests

WORKDIR /app/frontend
RUN mvn -B compile -DskipTests

EXPOSE 8080

CMD ["mvn", "jetty:run"]