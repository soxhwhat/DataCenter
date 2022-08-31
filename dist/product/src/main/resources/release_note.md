```
2203.5.23
【新增特性】
[特性1：增加服务端异常事件处理逻辑]
● 收集服务端异常上报以及呈现
● 删除日志收集模块，由于端口冲突，放到老的DataCollection中去收集

【修复】
[缺陷1: 产品默认未增加异常事件上报]
● [修改配置，增加异常事件上报]
● [修改扫描路径]

2203.5.22
【修改】
1.修改C09外呼数据统计相关代码

2203.5.21
【新增特性】
[特性1：话单相关数据推送到kafka]
● 收到排队机与SIP上报的话单数据后，根据话单推送接口约定拼接形成最终话单（话单信息中包含话单ID，主被叫号，通话开始/结束时间，通话时长，音频或视频各分辨率通话时长，呼入呼出类型），并推送到kafka
[特性2：新增外呼数据统计相关handler]
● 新增统计不同时间维度外呼数据的push成功率handler、拉起通话成功率、等待时长、通话时长handler

【修复】
[缺陷1: 修复dataCollection无法正常完全启动的问题]
● [@CubeStarterApplication添加扫描位置]
● [默认配置添加upload相关的配置]

【修改】
1.修改音视频质量观测平台部分数据处理逻辑

2203.5.20
【修复】
[缺陷1: 访客与坐席通话后，portal上无话单信息，诊断mongo入库集合名称问题。]
● [话单信息写入之前的集合]

2203.5.19
【修改】
1.埋点handler增加扫描 上传 移动文件以及清理的event
2.添加upload图片 音视频 打点的PO和mapper
3.增加音视频、图片、打点数据的插入handler。
4.添加uploadDbProcessor
5.埋点增加fileSize，recordTime，diffRecordTime字段

【待优化】
1.sqlite并发读写死锁的问题
2.统计并发总房间数不准确的问题

【配置】

## mysql (mysql/oracle二选一)
iron.datacenter.data-source.url=jdbc:mysql://192.168.2.110:3306/juphoon?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai
iron.datacenter.data-source.username=kiwi
iron.datacenter.data-source.password=juphoon419708
mybatis-plus.mapper-locations=classpath*:mysql/*Mapper.xml
## oracle (mysql/oracle二选一)
iron.datacenter.data-source.url=jdbc:oracle:thin:@//192.168.18.20:1521/ORCL
iron.datacenter.data-source.username=KIWI
iron.datacenter.data-source.password=123456
iron.datacenter.date-source.driver-class-name=oracle.jdbc.OracleDriver
mybatis-plus.mapper-locations=classpath*:oracle/*Mapper.xml
server.port=19986
spring.data.mongodb.event.uri=mongodb://juphoon:ENC(xISFbYRL8BMDkyH91VOkHOXnOlvUjpMp)@192.168.11.34:27017/jrtc_event?authSource=admin
spring.data.mongodb.log.uri=mongodb://juphoon:ENC(xISFbYRL8BMDkyH91VOkHOXnOlvUjpMp)@192.168.11.34:27017/jrtc_event?authSource=admin
spring.data.mongodb.md.uri=mongodb://juphoon:ENC(xISFbYRL8BMDkyH91VOkHOXnOlvUjpMp)@192.168.11.34:27017/jrtc_md_event?authSource=admin
spring.data.mongodb.record.uri=mongodb://juphoon:ENC(xISFbYRL8BMDkyH91VOkHOXnOlvUjpMp)@192.168.11.34:27017/jrtc_record?authSource=admin
spring.redis.host=192.168.11.34
spring.redis.password=ENC(qbEYCMQtB16tfBcOIyHW1Db5BjX0Mqdf)
spring.redis.port=6379
  
```