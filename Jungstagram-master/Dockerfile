FROM java:8

LABEL maintainer="pjhyl1127@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/Jungstagram-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} jungstagram.jar

ENTRYPOINT ["java","-jar","/jungstagram.jar"]