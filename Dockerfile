FROM openjdk:17-alpine
# ARG JAR_FILE=parking-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=/build/libs/parking-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} myboot.jar
ENTRYPOINT ["java", "-jar", "/myboot.jar"]

FROM redis
# COPY conf/redis.conf /usr/local/etc/redis/redis.conf
CMD [ "redis-server"]
EXPOSE 6380