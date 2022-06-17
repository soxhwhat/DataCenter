```java
    ├── data-core
    │   ├── accepter
    │   ├── config
    │   ├── handler
    │   ├── mq
    │   ├── processor
    │   ├── route
    │   └── service
    ├── dist
    │   ├── b01
    │   └── product
    └── doc
```


TODO:
1. 2022-06-15 郑建

当前消息消费模式是在最开始就指定了是单个依次消费还是批量消费，整个过程最开始就决定了后续消息是如何消费的。
但是实际情况下，消费者A需要单个消费，消费者B需要批量消费，比如，更新排队次数，需要单个消费，但是数据持久化，
可以批量执行，效率更高。因此，后续需要考虑不要在最开始就决定后续消息消费模式，尽量由消费方来决定消息如何消费。
那么，如何由消费者来决定呢？可以增加一层，由这一层来决定拆或者不拆。实际消费者通过继承不同的这一层，实现按需消费。

```java
AbstracHandler {
    @Override
    public void handler(List<T> list) {
        if next handler is handleSingle {
            list.foreach(next handler.handle(t));
        } else {
            next handler.handle(list);
        }
    }
    
    // 依次消费
    abstract handleSingle(T t);
    // 批量消费
    abstract handleBatch(List<T> list)
}
```

2. 2022-06-15 郑建

消费者线程池优化，相同的目标复用一个消费线程池，并且做好线程池大小的控制。

（广发线程池泄露）


3. 2022-06-15 郑建

disruptor消费者模型修改 


4. 2022-06-15

当前dc和portal的数据是完全分开的，两边的数据没有关联性，但是dc是生产方，portal是消费方，是否考虑将数据作为一个公共模块，Portal和dc公用？
如何公用？

5. 2022-06-16 郑建

关于redis功用的思考

redis定位为数据库还是缓存？如果是数据库，那么它可以成为关键路径；如果是缓存，那么它不能成为关键路径，只能作为雪中送炭。

那么如何决策它是否为关键路径呢？

正面：
1. 没它不行，只能依赖它实现，或者不用它实现成本太高承受不起，那么它就是关键路径

反面：
1. 可以用它实现，也可以不用它实现，只是用它实现的成本比较低（简单），那么它就是非关键路径
2. 在上层来看，非关键流程/功能，该功能不正常也无所谓，那么它就是非关键路径

其他
1. 不确定的情况下，那么它就是非关键路径


若是满足非关键路径条件，那么建议优先实现不依赖redis，让系统的对外依赖度将至最低

系统依赖度排序

Memory > Disk/(sqlite&server sync) > 关系型数据库 > MongoDB > Redis > ES/Kafka/OpenSearch/Flink/Spark/...

基于上述考虑，产品包中会先删去Redis部分代码，将其移植到 RDB 中

当前监控数据需要创建2张表

- StaffMonitor
- QueueMonitor

