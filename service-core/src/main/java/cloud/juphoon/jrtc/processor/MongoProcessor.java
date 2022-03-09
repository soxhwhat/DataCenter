package cloud.juphoon.jrtc.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/3 16:03
 * @Description:
 */
public class MongoProcessor extends AbstractEventProcessor {

    private Config config;

    @Autowired
    protected MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public Config getConfig() {
        return config;
    }

    public MongoProcessor(){
    }

    public MongoProcessor(Config config){
        this.config = config;
    }

    public static class Config {
        public Map<Integer,String> collectionMap = new HashMap<>();
    }

}
