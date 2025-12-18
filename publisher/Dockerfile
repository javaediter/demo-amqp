FROM openjdk:21-ea-34-oraclelinux7

LABEL authors="javaedi.et@gmail.com"

RUN adduser pub

WORKDIR /home/pub

ENV JAVA_OPTS="-Xms256m -Xmx1024m" \
    USER_DB=root \
    PASWD_DB=admin \
    URL_DB=jdbc:mysql://localhost:3306/books \
    RABBIT_HOST=localhost \
    FRONTEND_APP=localhost:4200 \
    JWT_SECRET=4kGz3wupkJmg5lLC6X2iolVVjJKIlD0Dut68FCnbRkM

COPY target/*jar pub.jar

USER pub

EXPOSE 9090

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar pub.jar"]