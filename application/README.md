# Simple Spring Boot app in Docker

Simple Spring Boot app in Docker. Shows multi-module application with `application` and `library` dependency.

## Run from the `application` directory

```bash
mvn install dockerfile:build
```

## Docker run

```bash
docker run -p8080:8080 bits/application:latest
```

Open the app at: http://localhost:8080/
