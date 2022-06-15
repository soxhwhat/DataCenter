package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.BaseContext;
import com.juphoon.rtc.datacenter.handler.AbstractHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;


/**
 * <p>mongodb操作handler抽象类</p>
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/8 17:37
 * @update
 * <li>1. 2022-03-29. ajian.zheng 抽象公共逻辑，将集合名放到上层，公共handle逻辑放到抽象层collectionName</li>
 * <li>2. 2022-03-31. ajian.zheng 支持多数据源，将数据源放到上层，公共handle逻辑放到抽象层mongoTemplate</li>
 */
@Slf4j
public abstract class AbstractMongoHandler<T extends BaseContext> extends AbstractHandler<T>
        implements IMongoCollectionManager {
    /**
     * 获取mongodb模板
     *
     * @return
     */
    public abstract MongoTemplate mongoTemplate();


}
