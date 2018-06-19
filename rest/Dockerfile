FROM openjdk:8

MAINTAINER ErikZ

COPY tablebase.jar /opt/tablebase/tablebase.jar
EXPOSE 8080

CMD java -jar /opt/tablebase/tablebase.jar --spring.datasource.url=${SPRING_DATASOURCE_URL}
