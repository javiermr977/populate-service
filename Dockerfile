FROM openjdk:11-slim

ARG YML
ARG JAR

ENV  APP_BASE="/usr/app" \
     JAR_FILE=$JAR \
     PROP_FILE=$YML

WORKDIR $APP_BASE
RUN chown -R 1000:1000 $APP_BASE
USER 1000

COPY ./build/libs/*.jar $APP_BASE/$JAR_FILE
COPY ./src/main/resources/populate-service.yml  $APP_BASE/$PROP_FILE

CMD java -Dspring.profiles.active=$ENVIRONMENT -Dserver.port=$PORT -Dapplication.container.version=1.0 $JAVA_MEM_ARGS -Dapplication.id=$APPLICATION_ID -Dapplication.log=$APP_BASE/logs -Dspring.config.location=$APP_BASE/$PROP_FILE -jar $JAR_FILE




