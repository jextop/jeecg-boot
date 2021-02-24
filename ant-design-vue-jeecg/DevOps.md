# 打包构建

- 修改前端项目的后台域名
    public/index.html  

- 域名
    - http://localhost:8080/jeecg-boot
    - http://127.0.0.1:8080/jeecg-boot
    - http://api.jextop.top/jeecg-boot
   
- 打包
    yarn run build

- 构建镜像
    docker-compose build

- 启动，如果有更新，自动取代运行中的容器
    docker-compose up -d

- 访问网页
  http://localhost:80

# 部署方式

- OSS，遇到了IE兼容问题
- Docker镜像

# 阿里云效DevOps流水线

## 打包

Node.js：14.8

npm install -g cnpm --registry=https://registry.npm.taobao.org

cnpm install 

npm run build

## 部署

cd /root/deploy

docker-compose pull

docker-compose up -d
