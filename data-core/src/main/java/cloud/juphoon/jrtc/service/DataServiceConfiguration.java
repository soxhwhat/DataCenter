package cloud.juphoon.jrtc.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    2/15/22 6:01 PM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Configuration
public class DataServiceConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DataService config() {
        //TODO
//        return DataServiceBuilder.processors()
//                .process(mysqlProcessConfig)
//                    .mq(mqConfig)
//                    .handler(new Ahandler())
//                    .handler(new Bhandler())
//                    .handler(new Chandler())
//                    .handler(new Dhandler())
//                    .end()
//                .process(mysql2ProcessConfig)
//                    .handler(new Whandler())
//                    .end()
//                .process(oracleProcessConfig)
//                    .mq(mqConfig)
//                    .handler(new Ehandler())
//                    .handler(new Fhandler())
//                    .end()
//                .process(kafkaProcessConfig)
//                    .mq(mqConfig)
//                    .handler(new Ghandler())
//                    .end()
//                .process(httpProcessConfig)
//                    .mq(mqConfig)
//                    .handler(new Hhandler())
//                    .handler(new Ihandler())
//                    .handler(new Jhandler())
//                    .end()
//                .build();
        return null;
    }
}
