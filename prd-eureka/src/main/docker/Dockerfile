FROM openjdk:8
VOLUME /tmp
ADD prd-eureka.jar app.jar
RUN bash -c 'touch /eureka-app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eureka-app.jar"]
