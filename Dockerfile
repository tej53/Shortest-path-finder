FROM eclipse-temurin:25

WORKDIR /app

COPY ShortestPathFinder.jar app.jar

CMD ["java", "-jar", "app.jar"]