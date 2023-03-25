FROM openjdk:19
EXPOSE 8081
VOLUME /var/lib/postgresql/data
LABEL maintainer="mohamedyousif093@gmail.com"
WORKDIR /ParserToolAPP
COPY target/ParserToolAPP-0.0.1-SNAPSHOT.jar ParserToolAPP-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","ParserToolAPP-0.0.1-SNAPSHOT.jar"]