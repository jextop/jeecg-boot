# 打包构建

- 运行maven打包
    mvn clean package -Dmaven.test.skip=true --settings ./settings.xml
 
- 构建镜像
    docker-compose build

- 启动容器
    docker-compose up

- 访问服务
    http://localhost:8080/jeecg-boot/chk

- 访问API文档，注意要开启swagger
    http://localhost:8080/jeecg-boot/doc.html

# 阿里云效DevOps流水线

## 打包

mvn -B clean package -Dmaven.test.skip=true -Dautoconfig.skip --settings ./settings.xml

## 部署

cd /root/deploy

docker-compose pull

docker-compose up -d
