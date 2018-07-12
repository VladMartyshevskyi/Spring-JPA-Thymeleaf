FROM java:8
EXPOSE 8080
ADD /target/spring-thymeleaf-jpa-0.0.1-SNAPSHOT-spring-boot.jar spring-thymeleaf-jpa-0.0.1-SNAPSHOT-spring-boot.jar
ENTRYPOINT ["java", "-jar", "spring-thymeleaf-jpa-0.0.1-SNAPSHOT-spring-boot.jar"]
