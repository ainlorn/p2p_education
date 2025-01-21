FROM amazoncorretto:17-alpine-full

WORKDIR /opt/app
COPY ./build/libs/*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
