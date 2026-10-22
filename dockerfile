FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/app.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
CMD ["java","-jar","app.jar"]