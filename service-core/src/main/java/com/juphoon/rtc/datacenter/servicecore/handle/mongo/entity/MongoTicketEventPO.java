package com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @date 7/29/22 19:26 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Document("jrtc.records")
@Getter
@Setter
public class MongoTicketEventPO extends MongoEventPO{

}
