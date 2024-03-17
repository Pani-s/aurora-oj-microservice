# Docker 镜像构建
# @author pani
#
FROM openjdk:8
ADD aurora-oj-user-service-0.0.1-SNAPSHOT-exec.jar aurora-oj-user-service-0.0.1-SNAPSHOT-exec.jar
ENTRYPOINT java -Xmx256m -jar -Duser.timezone=GMT+08  aurora-oj-user-service-0.0.1-SNAPSHOT-exec.jar  --spring.profiles.active=prod