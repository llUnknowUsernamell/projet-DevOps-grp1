FROM openjdk:11-jdk-slim AS builder

RUN apt-get update && \
    apt-get install -y maven curl firefox-esr && \
    rm -rf /var/lib/apt/lists/* \

WORKDIR /app

COPY ./_00_ASBank2023 .

COPY ./drivers/geckodriver /app/drivers/geckodriver

RUN mvn -B -f pom.xml dependency:resolve

CMD bash -c "mvn clean test -Pselenium-tests && tail -f /dev/null"