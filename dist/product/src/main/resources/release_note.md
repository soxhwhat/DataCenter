
#		版本分支: 2203
##		稳定版本：<2301.0.1 20221104> [release]
###		发布者：黄佳辉
【升级必看】 修复了多个processor无法处理相同事件的问题

【新增特性】
[特性1：新增排队机视频客服埋点]
● 埋点handler新增排队机视频客服埋点

【修复】
[缺陷1: 多个processor无法处理相同事件的问题]
● 每次对BaseContext处理之前进行深拷贝，避免多个processor处理同一个事件时，数据被覆盖

#		版本分支: 2203
##		稳定版本：<2203.6.2 20221021> [release]
###		发布者：黄佳辉
【升级必看】 无

【新增特性】
[特性1：诊断事件添加会场相关事件]
● 埋点handler新增关注会场事件

#		版本分支: 2203
##		稳定版本：<2203.6.1 20221012> [release]
###		发布者：黄佳辉
【升级必看】 无

【新增特性】
[特性1：添加监控报表相关数据处理]
● 视频客服队列状态人数统计
● 视频客服坐席状态统计

#		版本分支: 2203
##		稳定版本: <2203.6.0 20220916> [release]
###		发布者：黄佳辉
【升级必看】
[重要特性说明] 该版本彻底解决了sqlite并发读写时死锁导致的事件丢失，意味着DataCollection服务提供更加可靠完善的机制来保证事件会被正确处理。**建议原本旧版本优先升级到该版本**。

【优化】
[优化1：重写sqlite代码复用逻辑]
● 消除原本逻辑操作数据库的冗余代码
● 解决原本sqlite代码复用存在的逻辑问题
[优化2：优化实时房间和实时成员统计逻辑]
● 解决原本统计逻辑会造成部分情况下数据偏差较大的问题

【修复】
[缺陷1: 带有打点信息的视频数据入库失败]
● 修改UploadAddVideoDatabaseHandler解析打点数据逻辑

#		版本分支: 2203
##		稳定版本：<2203.5.24 20220902> [release]
###		发布者：黄佳辉
【升级必看】
[重要特性说明] 解决了测试环境MongoDB数据激增导致磁盘空间不足的问题。

【新增特性】
[特性1：修改事件入库流程与逻辑]
● 去除事件入库时Mybatis依赖，依靠原生api操作sqlite
● 修复并发读写时死锁导致的事件丢失
[特性2：添加定时清除mongoDB数据功能]
● 支持可配置定时清除mongoDB数据，防止数据增速过快导致磁盘空间不足

【修改】
1.删除日志相关的接口和逻辑
2.调整redo逻辑
3.增加接口上传行为埋点

【配置】
埋点集合过期时间配置: iron.datacenter.clear.expire-md = 7 # (单位：天,测试环境可以设置过期时间为7天)
事件集合过期时间配置: iron.datacenter.clear.expire-event = 7 # 单位：天,测试环境可以设置过期时间为7天


#		版本分支: 2203
##		稳定版本：<2203.5.23 20220825> [release]
###		发布者：黄佳辉
【升级必看】 无

【新增特性】
[特性1：增加服务端异常事件处理逻辑]
● 收集服务端异常上报以及呈现
● 删除日志收集模块，由于端口冲突，放到老的DataCollection中去收集

【修复】
[缺陷1: 产品默认未增加异常事件上报]
● [修改配置，增加异常事件上报]
● [修改扫描路径]

#		版本分支: 5.0
##		稳定版本：<2203.5.22 20220818> [release]
###		发布者：黄佳辉
【升级必看】 无

【修改】
1.修改C09外呼数据统计相关代码

#		版本分支: 5.0
##		稳定版本：<2203.5.21 20220808> [release]
###		发布者：黄佳辉
【升级必看】 无

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

#		版本分支: 5.0
##		稳定版本：<2203.5.20 20220730> [release]
###		发布者：袁文军
【升级必看】 无

【修复】
[缺陷1: 访客与坐席通话后，portal上无话单信息，诊断mongo入库集合名称问题。]
● [话单信息写入之前的集合]

#		版本分支: 5.0
##		稳定版本：<2203.5.19 20220729> [release]
###		发布者：王珂
【升级必看】 无

【修改】
1.埋点handler增加扫描、上传、移动文件以及清理的event
2.添加upload图片 音视频 打点的PO和mapper
3.增加音视频、图片、打点数据的插入handler。
4.添加uploadDbProcessor
5.埋点增加fileSize，recordTime，diffRecordTime字段

【待优化】


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
spring.data.mongodb.md.uri=mongodb://juphoon:ENC(xISFbYRL8BMDkyH91VOkHOXnOlvUjpMp)@192.168.11.34:27017/jrtc_md_event?authSource=admin
spring.data.mongodb.record.uri=mongodb://juphoon:ENC(xISFbYRL8BMDkyH91VOkHOXnOlvUjpMp)@192.168.11.34:27017/jrtc_record?authSource=admin
spring.redis.host=192.168.11.34
spring.redis.password=ENC(qbEYCMQtB16tfBcOIyHW1Db5BjX0Mqdf)
spring.redis.port=6379
  
