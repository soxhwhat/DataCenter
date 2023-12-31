package com.juphoon.rtc.datacenter.datacore;

/**
 * <p>常量定义</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/2 16:38
 */
public class JrtcDataCenterConstant {

    public static final String CONNECTION_CLOSED = "database connection closed";
    public final static String DOMAIN_CODE = "J61";

    public final static int CACHE_NUMBER = 100;

    public final static String URL_PARAMS = "?name=Juphoon";
    public final static String KEY_AGREE = "AGREE";

    public final static Integer STATE_READYING = 0;
    public final static Integer STATE_LOADING = 1;

    public static final String THEA_SERVER_COLLECTION = "thea_server_collection";
    public static final String THEA_CLIENT_COLLECTION = "thea_client_collection";
    public static final String COLLECTION_LOG_ENDPOINT = "collection.logEndpoint";
    public static final String COLLECTION_INFO_ENDPOINT = "collection.infoEndpoint";
    public static final int THEA_SERVER_CODE = 901;
    public static final int THEA_CLIENT_CODE = 900;
    public static final String UNDER_LINE = "_";
    public static final String EVENT_COLLECTION = "event_collection";
    public static final String VERSION_SOURCE = "_source";
    public static final String POINT = ".";
    public static final String FROM = "from";
    public static final String TAGS = "tags";

    /**
     * SQLITE 数据库文件路径
     */
    public static final String LOCAL_DB_FILE_BASE_PATH = "/.db/";

    /**
     * SQLITE 快速数据库文件名
     */
    public static final String LOCAL_DB_FILE_FLASH = "flash.db";
    /**
     * SQLITE 可靠数据库文件名
     */
    public static final String LOCAL_DB_FILE_RELIABLE = "reliable.db";

    /**
     * datacenter配置前缀
     */
    public static final String DATA_CENTER_CONFIG_PREFIX = "iron.datacenter";
    /**
     * 服务水平配置前缀
     */
    public static final String DATA_CENTER_SERVICE_LEVEL_CONFIG_PREFIX = DATA_CENTER_CONFIG_PREFIX + ".servicelevel";

    /**
     * 时间窗口配置前缀
     */
    public static final String DATA_CENTER_WINDOW_LEVEL_CONFIG_PREFIX = DATA_CENTER_CONFIG_PREFIX + ".windowlevel";
    public static final String KAFKA_PREFIX = "kafka";

    public static final String DATABASE_TYPE_MYSQL = "mysql";
    public static final String DATABASE_TYPE_ORACLEL = "oracle";

    /**
     * log日志收集
     */
    public static final String TIMESTAMP = "timestamp";
    public static final String VERSION_KEY = "_ver";
    public static final String ID_KEY = "_id";
    public static final Integer VERSION_VALUE = 1;
    public static final String UNDERLINE_TIMESTAMP = "_timestamp";
    public static final String UNDERLINE_VERSION_KEY = "_ver";
    public static final Integer UNDERLINE_VERSION_VALUE = 1;
    public static final String SOURCE_INFO_KEY = "info";
    public static final String TYPE = "_type";


    /**
     * scope
     */
    public static final String RESOURCE_SCOPE_GLOBAL_EVENT = "globalEvent";
    public static final String RESOURCE_SCOPE_GLOBAL_LOG = "globalLog";
    public static final String RESOURCE_SCOPE_GLOBAL_STATE = "globalState";
    public static final String RESOURCE_SCOPE_INNER = "inner";
    public static final String RESOURCE_SCOPE_TEST = "test";
    public static final String RESOURCE_SCOPE_B01 = "b01";
    public static final String RESOURCE_SCOPE_B03 = "b03";
    public static final String RESOURCE_SCOPE_H20 = "h20";
    public static final String RESOURCE_SCOPE_E01 = "e01";
    public static final String RESOURCE_SCOPE_C09 = "c09";
    public static final String RESOURCE_SCOPE_AGREE = "agree";

    /**
     * 可靠事件处理器
     */
    public static final String EVENT_BIN_LOG_IMPL_RELIABLE = "reliableEventBinLogServiceImpl";
    public static final String EVENT_BIN_LOG_IMPL_FLASH = "flashEventBinLogServiceImpl";
    public static final String EVENT_BIN_LOG_IMPL_NONE = "noneEventBinLogServiceImpl";

    public static final String LOG_BIN_LOG_IMPL_FLASH = "flashLogBinLogServiceImpl";
    public static final String LOG_BIN_LOG_IMPL_NONE = "noneLogBinLogServiceImpl";

    public static final String STATE_BIN_LOG_IMPL_FLASH = "flashStateBinLogServiceImpl";
    public static final String STATE_BIN_LOG_IMPL_NONE = "noneStateBinLogServiceImpl";


    /**
     * queue service config 参数
     */
    public static final String QUEUE_SERVICE_CONFIG_TYPE_NONE = "none";
    public static final String QUEUE_SERVICE_CONFIG_TYPE_EXECUTOR = "executor";

    /**
     * processor type
     */
    public static final String PROCESSOR_TYPE_EVENT = "event";
    public static final String PROCESSOR_TYPE_STATE = "state";
    public static final String PROCESSOR_TYPE_TEST = "test";


    /**
     * mongodb template names - 事件
     */
    public static final String MONGO_TEMPLATE_EVENT = "eventMongoTemplate";
    /**
     * mongodb template names - 话单
     */
    public static final String MONGO_TEMPLATE_RECORD = "recordMongoTemplate";
    /**
     * mongodb template names - 埋点
     */
    public static final String MONGO_TEMPLATE_MD = "mdMongoTemplate";

    //***************************************************************************************************
    //********************************** 视频客服 坐席状态常量**********************************************
    //***************************************************************************************************
    // 参考事件登记簿 https://juphoon.yuque.com/videoproducts/wboebb/rc884g#kCrl
    /**
     * 示闲=1
     */
    public static final Integer ACD_AGENT_STATE_FREE = 1;
    /**
     * 分配中=2
     */
    public static final Integer ACD_AGENT_STATE_ALLOC = 6;
    /**
     * 振铃=3
     */
    public static final Integer ACD_AGENT_STATE_RINGING = 6;
    /**
     * 通话=4
     */
    public static final Integer ACD_AGENT_STATE_TALKING = 6;
    /**
     * 示忙=5
     */
    public static final Integer ACD_AGENT_STATE_BUSY = 6;
    /**
     * 签出=6
     */
    public static final Integer ACD_AGENT_STATE_CHECK_OUT = 6;
    /**
     * 掉线=7
     */
    public static final Integer ACD_AGENT_STATE_OFFLINE = 6;
}
