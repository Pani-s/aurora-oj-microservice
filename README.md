# Aurora 在线编程评测平台微服务后端
在线访问：[在线访问](http://oj.soogyu.xyz)

---

前端：[Pani-s/aurora-oj-frontend: Aurora在线判题系统前端 (github.com)](https://github.com/Pani-s/aurora-oj-frontend)

代码沙箱服务：[Pani-s/aurora-oj-code-sandbox: Aurora在线判题系统代码沙箱模块 (github.com)](https://github.com/Pani-s/aurora-oj-code-sandbox)

单体后端（后续功能未更新）：[Pani-s/aurora-oj-backend: Aurora在线判题系统后端 (github.com)](https://github.com/Pani-s/aurora-oj-backend)

---

## 项目介绍

### 介绍:

本项目是一个基于 **Spring Cloud 微服务架构**、**消息队列 (MQ)** 和 **Docker 容器化技术** 的在线编程题目评测系统。该系统旨在为编程学习者、教育机构以及企业提供代码评测服务，通过预设的题目用例对用户提交的代码进行自动化测试，并即时返回评测结果。

此外，系统中**自主研发的代码沙箱**可作为独立服务供其他开发者调用。

### ***预览：***


<img src="http://pics.soogyu.xyz/pani/oj/intro/doquestion.webp" style="zoom: 40%;" />

<img src="http://pics.soogyu.xyz/pani/oj/intro/question-view.webp" style="zoom: 40%;" />

<img src="http://pics.soogyu.xyz/pani/oj/intro/submit-view.webp" style="zoom: 40%;" />

<img src="http://pics.soogyu.xyz/pani/oj/intro/judge-config.webp" style="zoom: 40%;" />

## 功能特点

- **自动评测**：用户提交代码后，系统将自动运行预设的测试用例进行评测。

- **即时反馈**：评测结果即时返回，用户可以实时查看代码的正确性及性能。

- **代码沙箱**：自主研发的代码沙箱确保用户提交的代码在一个安全、隔离的环境中运行，防止恶意代码执行。

- **高可用性**：系统采用微服务架构，结合 Docker 容器化技术，保证服务的高可用性和可扩展性。

- **易于集成**：代码沙箱可以作为独立服务供其他开发者调用，方便集成到第三方平台。

- **文档管理与维护**：整合Knife4j Gateway，实现各服务Swagger接口文档的统一聚合，简化文档管理流程。

  

- **在线代码编辑器**：内置代码编辑器，支持语法高亮、代码补全等特性，提供良好的编程体验。

- **排行榜**：根据用户完成题目的数量和质量，展示排行榜，激励用户积极参与。

- **题目分类**：题目可以按照类型、知识点等进行分类，方便用户查找和筛选。

- **题目管理**：允许管理员添加、编辑和删除编程题目，以及上传测试用例。

- **用户管理**：支持用户的注册、登录、个人资料管理等功能。

- **评测结果**：提供详细的评测结果，包括通过状态、错误信息、运行时间、内存使用情况等。

### TODO:

#### 功能：

- **社交互动**：提供讨论区，用户可以在此交流解题心得，互相帮助。
- **题目难度分级**：题目可以根据难度分级，便于用户根据自己的水平选择合适的题目。
- **题目收藏点赞**：用户可以对题目进行收藏，并可在个人收藏夹中查看。
- **排行榜完善**：日、周、月排行榜、综合排行。
- **数据统计**：利用 AI 收集和分析用户行为数据，为用户提供个性化推荐，帮助用户提高编程技能。
- **第三方判题API引入**：Judge0。
- **ACM模式**：ACM模式判题。
- 用户修改头像。

#### 系统：

- Sentinel 实现微服务的限流、熔断与降级。（4G服务器表示真的抗不下了）

## 项目架构

<img src="http://pics.soogyu.xyz/pani/oj/%E6%9E%B6%E6%9E%84%E5%9B%BE1.drawio.png" style="zoom: 80%;" />

## 技术栈

### **前端**
- Vue 3
- Vue-Cli 脚手架
- Vuex状态管理
- Arco Design组件库
- 前端工程化：ESlint + Prettier + TypeScript
- 手写前端项目模板（通用布局，权限管理，状态管理，菜单生成）
- Markdown 富文本编辑器
- Monaco Editor 代码编辑器
- OpenAPI前端代码生成

### **后端**
- Java Spring Boot
- Java spring cloud + Spring Cloud Alibaba 微服务
  - Nacos注册中心
  - OpenFeign 客户端调用
  - Gateway 网关
  - 聚合接口文档
- Java Spring Boot (后端模板）
- Java 进程控制
- Java 安全管理器
- Docker代码沙箱实现
- MyBatis-Plus 及 MyBatis X自动生成
- Redis 分布式 Session , Redisson 限流
- RabbitMQ 消息队列
- 多种设计模式
  - 策略模式
  - 工厂模式
  - 代理模式
  - 模板方法模式
- Swagger + Knife4j 接口文档生成
- Hutool、Apache Common Utils 等工具库

**数据存储**

- MySQL 数据库
- 七牛云 对象存储



## 项目启动

### 后端

- 下载/拉取本项目到本地
- 通过 IDEA 代码编辑器进行打开项目，等待依赖的下载
- 修改配置文件 `application.yaml` 的信息，比如数据库、Redis、RabbitMQ等
- 修改信息完成后，一键运行项目
