# Monolite Weather

## Descrizione
Applicazione Spring Boot per fornire dati meteo per diversi paesi, con API REST documentate via Swagger UI.

---

## Build

Per compilare il progetto Java con Maven
./mvnw clean install 

## Costruzione di un container
docker build -t monolite-weather .

## AVVIO di un container
docker run -p 3080:8080 monolite-weather

## Documentazione Relativa alle Api
http://localhost:8080/swagger-ui.html