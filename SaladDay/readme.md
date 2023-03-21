## 基本问题

1. 孤军奋战永远比不上团队作战，在码字的时候，很多情况下思路是封闭的，并且由于技术种类多实现多往往很难自己想到使用某某技术来实现功能，想了半天结果还是用很蠢的方式来做，这种时候如果有一帮志同道合的伙伴，很可能会获得让人耳目一新的好点子。但目前本人认识的伙伴要么是搞算法要么搞其他什么玩意，对Web有研究的很少，所以我参加321CQU的一个目的是为了这个。其次我还想获得一些实际项目的经验，目前我觉得我最缺乏的就是这个，很多时候只能做一些小DEMO。

2. 我接触的编程语言其实蛮多的，用的最顺手的是Java和Python(Python目前主要是用的Torch和pandas)，C和Cpp在大一的时候教了，但是好久没用了(~~结构体都定义不来了~~)。前端也有接触，呃，如果微信小程序那个算Vue的话那Vue应该算做过项目，Vue cli做过小Demo。目前主要做的项目有：

   - 微信小程序：重庆大学实验室保修系统，现在正在内测，很快会在计算机实验室推广使用。用的是微信小程序的云开发，node做的。

   其他做的都是小Demo，都在git仓库了。

3. 期望参加的方向的话，后端、小程序、安卓都可以，但是安卓开发得学...现在还不会...但是应该不是特别难吧...

4. 自我介绍一下吧，21计卓罗帆靖...其他的好像也没啥好说了

   - 联系方式：
     - saladday@foxmail.com
     - QQ:1203511142



## 项目设计(后端---服务器开发)

#### 目录结构：

- SaladDay
  - logs 日志
  - Recruit_321cqu MAVEN项目文件，源代码
  - readme.md
  - recruitApi.html api文档

### How to start it：

- maven构建

- 启动Redis-service 使用默认端口6379，主机localhost，默认无密码
  - 若要改请在applicaiton.yml中更改
- 启动mysql 使用默认端口3306, 主机localhost,账号密码在application.yml中更改
- 启动Recruit321cquApplication
- 前缀域名为 http://localhost:8080

#### 技术:

- SpringBoot
- fastjson
- mysql(要求中的sqlite没学过，用mysql代替)
- druid
- mybatisplus
- junit
- redis && jedis

- ~~swagger~~(后期想着API本身也没几个，不如我手打)

#### 介绍：

简要介绍一下各个功能的实现方法：

- 成绩查询缓存：第一层使用数据库缓存，第二层使用redis缓存。

<img src="https://saladday-figure-bed.oss-cn-chengdu.aliyuncs.com/img/image-20230321184110022.png" alt="image-20230321184110022" style="zoom: 50%;" />

- 成绩记录：数据库的设计架构如图，因时间有限，字段名设计略显潦草。

<img src="https://saladday-figure-bed.oss-cn-chengdu.aliyuncs.com/img/image-20230321181052916.png" alt="image-20230321181052916" style="zoom:50%;" />

- 错误处理：自定义异常类CustomException和全局异常处理器GlobalExceptionHandler

- 日志记录：

  - 程序运行(报错)日志存储在本地，15天清理。往日的报告会在次日加上日期后缀。

    <img src="https://saladday-figure-bed.oss-cn-chengdu.aliyuncs.com/img/image-20230321181428037.png" alt="image-20230321181428037" style="zoom: 67%;" />

  - 用户操作日志存储在数据库，不自动清理。

  <img src="https://saladday-figure-bed.oss-cn-chengdu.aliyuncs.com/img/image-20230321181546469.png" alt="image-20230321181546469" style="zoom:67%;" />

- 单元测试：由于没有相关规范，本人单元测试还是用的比较随心所欲。对于以上功能的测试，会提供apiFox的测试文档(如果需要测试的话，可以QQ私信，提供apifox的文件，现提供了Api文档在SaladDay目录下)，实现100%覆盖。

#### Api说明：

##### 1.	../login

POST

| requestParams |          |      |
| ------------- | -------- | ---- |
| apiKey        | required |      |
| applyType     | required |      |
| username      | required |      |
| password      | required |      |

在原有接口中增加了以下功能：

- 记录操作日志
- saveOrUpdate 用户信息到user表，其中password通过md5加密
- saveOrUpdate Token信息到token表并且与user表关联

##### 2. 	../refresh

POST

| requestParams |          |      |
| ------------- | -------- | ---- |
| refreshToken  | required |      |

在原有接口中增加了以下功能：

- 记录操作日志
- update Token 信息到token表

##### 3.	../gradesUaP 通过username和password查询grades

POST

| requestParams |          |      |
| ------------- | -------- | ---- |
| username      | required |      |
| password      | required |      |
| apiKey        |          |      |
| applyType     |          |      |

此接口工作逻辑较复杂：

- 访问此API，提供username和password，在DB中查询，如果查询无此用户或者密码错误。
  - 若没有提供apiKey && applyType，抛出异常
  - 若提供了，则调用 ../login API
    - 此时若成功返回，(此时DB中user和token均被更新)，继续查询grade
    - 若此时未成功返回，抛出异常
- 如果查询此用户存在并且密码正确。
  - 查询redis是否有数据，若有，直接返回
  - 查询DB是否有数据，若有，直接返回
  - 检查token是否过期
    - 若过期，检查refresh是否过期
      - 若过期，同在DB中查询，查询无此用户或者密码错误
      - 若未过期，发送 ../refresh请求
    - 若未过期，发送api请求grades信息

---



- 查询grade：
  - 首先在redis中查询
  - 再在数据库中查询
  - 最后调用api查询



在原有接口中增加了以下功能：

- 记录操作日志
- 将查询到的数据记录数据库
- 二层缓存，避免多次发送api请求
- 提供用户名密码查询功能
- 整合login接口



##### 4. 	../gradesToken 通过token查询grades

由于时间问题，暂时搁置，思路与3.差不多。
