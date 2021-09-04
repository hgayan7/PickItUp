FROM openjdk:13
ADD target/pickItUp.jar pickItUp.jar
EXPOSE 8100
ENTRYPOINT ["java", "-jar", "pickItUp.jar"]