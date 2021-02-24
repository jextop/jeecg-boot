FROM registry.cn-shanghai.aliyuncs.com/hellodock/openjdk:8

LABEL maintainer="Jext Community, https://github.com/jextop"

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /deploy
WORKDIR /deploy

COPY ./deploy/ ./
ADD ./jeecg-boot-module-system/target/jeecg-boot-module-system-2.2.1.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar jeecg-boot-module-system-2.2.1.jar

VOLUME /deploy/upFiles

EXPOSE 8080
