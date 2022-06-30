package com.juphoon.rtc.datacenter.servicecore.handle.mongo;

import com.juphoon.rtc.datacenter.datacore.api.BaseContext;
import com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/13/22 7:21 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface IMongoCollectionManager {
    /**
     * 是否按天拆分
     * <p>按天拆分有利于空间回收</p>
     * @return true/false
     */
    default boolean collectionStorageDaily() {
        return false;
    }

    /**
     * 返回集合名枚举
     *
     * @return 返回集合名枚举
     */
    MongoCollectionEnum collectionName();

    /**
     * 按天拆分
     *
     * @param handler
     * @param context
     * @return
     */
    default String getCollectionName(AbstractMongoHandler handler, BaseContext context) {
        String name = handler.collectionName().getName();
        if (handler.collectionStorageDaily()) {
            return name + DateFormatUtils.format(new Date(context.getCreatedTimestamp()), "yyyyMMdd");
        } else {
            return name;
        }
    }
}
