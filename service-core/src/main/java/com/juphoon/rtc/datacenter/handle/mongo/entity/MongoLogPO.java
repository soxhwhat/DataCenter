package com.juphoon.rtc.datacenter.handle.mongo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.StringJoiner;

/**
 * <p>Mongodb日志存储PO</p>
 *{
 *     "_id" : ObjectId("6295bd9b4d97a002f88bfedc"),
 *     "_timestamp" : NumberLong(1653979941126),
 *     "_ver" : 1,
 *     "_source" : {
 *         "tags" : [
 *             "0190f8e3-c2da-103a-846a-a72dbebac6ed"
 *         ],
 *         "type" : "JSM",
 *         "info" : {
 *             "log" : "20220531T14:52:21.126+0800  MAIN(140556540672128)    JMP:  INFO: SetAsufficBitRate: 356 kbps"
 *         }
 *     }
 * }
 * @author ajian.zheng@juphoon.com
 * @date 6/14/22 23:26 PM
 */
@Document("test_log_#{new java.text.SimpleDateFormat(\"yyyyMMdd\").format(new java.util.Date())}")
@Getter
@Setter
public class MongoLogPO {
    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 时间戳
     */
    @Indexed(direction = IndexDirection.DESCENDING)
    @Field("_timestamp")
    @JsonProperty("_timestamp")
    private Long timestamp;

    @Field("_ver")
    private Integer _ver;

    @Field("_source")
    private Source _source;

    @Data
    public static class Source {
        @Indexed
        private List<String> tags;

        private String type;

        private Info info;

        @Data
        public static class Info {
            private String log;
        }
    }

    /**
     * 其他参数
     */
    @SneakyThrows
    public static MongoLogPO fromString(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(data, MongoLogPO.class);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MongoLogPO.class.getSimpleName() + "[", "]")
                .add("domainId=" + domainId)
                .add("appId=" + appId)
                .add("timestamp=" + timestamp)
                .add("version=" + _ver)
                .add("source=" + _source)
                .toString();
    }
}
