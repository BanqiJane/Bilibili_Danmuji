FROM gradle:6.8-jdk8 AS buildingImage

ADD . /home/gradle/project
#USER gradle
WORKDIR /home/gradle/project

RUN set -x; gradle build
RUN ls build/libs/*.jar | grep -v plain.jar | head -n 1 | xargs -I {} mv {} /home/app.jar

FROM eclipse-temurin:8-jre
ENV TZ Asia/Shanghai

COPY --from=buildingImage /home/app.jar /workspace/app.jar
COPY --from=buildingImage /home/gradle/project/deploy/docker/entrypoint.sh /workspace/entrypoint.sh
WORKDIR /workspace
ENTRYPOINT bash entrypoint.sh