package com.juphoon.rtc.datacenter.constant;/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ke.wang@juphoon.com
 * @date    2022/3/2 16:38
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */ 
public class JrtcDataCenterConstant {

    public final static String URL_PARAMS = "?name=Juphoon";
    public final static String KEY_AGREE = "AGREE";

    public final static Integer STATE_READYING = 0;
    public final static Integer STATE_LOADING = 1;

    public static final String THEA_SERVER_COLLECTION = "thea_server_collection";
    public static final String THEA_CLIENT_COLLECTION = "thea_client_collection";
    public static final int THEA_SERVER_CODE = 901;
    public static final int THEA_CLIENT_CODE = 900;
    public static final String UNDER_LINE = "_";
    public static final String TIMESTAMP = "timestamp";
    public static final String EVENT_COLLECTION = "event_collection";
    public static final String VERSION_SOURCE = "_source";
    public static final String POINT = ".";
    public static final String TAGS = "tags";


    //***************** 数据库模块 bean-name ***********************************
    /**
     * DatabaseEventProcessor bean name
     */
    public static final String DATABASE_EVENT_PROCESSOR = "databaseEventProcessor";
    /**
     * JrtcAcdCallinfoStatDailyHandler bean name
     */
    public static final String CALLINFO_STAT_DAILY = "callinfoStatDaily";
    /**
     * JrtcAcdCallinfoStatPartHandler bean name
     */
    public static final String CALLINFO_STAT_PART = "callinfoStatPart";


    /**
       http 模块
     */
    public static final String NOTICE_HANDLER = "noticeHandler";
    public static final String NOTICE_PROCESSOR = "noticeHttpProcessor";
    public static final String SEND_ONLINE_HANDLER = "sendOnlineHandler";


    /**
     http mongo kafka 模块
     */
    public static final String EVENT_MONGO_HANDLER = "eventMongoHandler";
    public static final String THEA_MONGO_HANDLER = "theaMongoHandler";
    public static final String MONGO_PROCESSOR = "mongoProcessor";
    public static final String EVENT_KAFKA_HANDLER = "eventKafkaHandler";
    public static final String KAFKA_PROCESSOR = "kafkaProcessor";

    /**
     * datacenter配置前缀
     */
    public static final String DATA_CENTER_CONFIG_PREFIX = "iron.datacenter";

}
