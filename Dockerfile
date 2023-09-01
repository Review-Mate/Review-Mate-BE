FROM amazoncorretto:17
EXPOSE 8080
ARG JAR_FILE=./build/libs/reviewmate-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=test","/app.jar", "--logging.file.path=/Users/chan/code/java/review-mate-be/log",">> /Users/chan/code/java/review-mate-be/log/deploy.log"]