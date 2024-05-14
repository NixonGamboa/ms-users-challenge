FROM openjdk:17-oracle

RUN mkdir -p /home/app

COPY ./runner/app-service/build/libs/ms-users.jar ./home/app

WORKDIR ./home/app

EXPOSE 8080

CMD ["java", "-jar", "ms-users.jar"]
