FROM openjdk:8
COPY target/msservice-1.0.jar usr/local/msservice-1.0.jar
CMD ["java","-jar","usr/local/msservice-1.0.jar"]