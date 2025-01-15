FROM mariadb:10.5 AS mariadb
COPY ./docker_databases/*.sql /docker-entrypoint-initdb.d/


FROM openjdk:11-jdk-slim AS builder

RUN apt-get update && apt-get install -y maven curl

RUN curl -o /wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && chmod +x /wait-for-it.sh


WORKDIR /app

COPY ./_00_ASBank2023 .

COPY ./wait-for-warFile.sh .

RUN chmod u+x ./wait-for-warFile.sh

RUN mvn -B -f pom.xml dependency:resolve


CMD ["/bin/bash", "-c", "rm -rf /etc/temp_built/* && /wait-for-it.sh db_asbank:3306 -t 60 -- mvn clean package && ./wait-for-warFile.sh ./target/*.war && cp -r /app/target/*.war /etc/temp_built"]





FROM tomcat:9.0-jdk11-openjdk-slim AS execution

RUN apt-get update && apt-get install -y bash

RUN rm -rf /usr/local/tomcat/webapps/*


EXPOSE 8080

CMD ["/bin/bash", "-c", "while [ ! -f /etc/temp_built/*.war ]; do echo 'En attente du fichier .war'; sleep 5; done && cp -r /etc/temp_built/*.war /usr/local/tomcat/webapps/AS_Bank.war && catalina.sh run"]

