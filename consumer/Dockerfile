FROM openjdk:21-ea-34-oraclelinux7

LABEL authors="javaedi.et@gmail.com"

RUN adduser con

WORKDIR /home/con

ENV JAVA_OPTS="-Xms256m -Xmx512m" \
    USER_DB=root \
    PASWD_DB=admin \
    URL_DB=jdbc:mysql://localhost:3306/books \
    RABBIT_HOST=localhost \
    FRONTEND_APP=localhost:4200 \
    MONGO_URL=mongodb://localhost:27017/loans_books \
    JWT_SECRET=4kGz3wupkJmg5lLC6X2iolVVjJKIlD0Dut68FCnbRkM

COPY target/*jar con.jar

USER con

EXPOSE 9091

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar con.jar"]