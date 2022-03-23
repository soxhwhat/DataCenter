package com.juphoon.rtc.datacenter;

import com.juphoon.rtc.datacenter.handle.TestHttpEventHandler;
import com.juphoon.rtc.datacenter.handle.Xhandler;
import com.juphoon.rtc.datacenter.handle.Yhandler;
import com.juphoon.rtc.datacenter.processor.TestEventProcessor;
import com.juphoon.rtc.datacenter.processor.HttpClientEventProcessor;
import com.juphoon.rtc.datacenter.service.DataService;
import com.juphoon.rtc.datacenter.service.DataServiceBuilder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>测试类</p>
 * <p>构造测试的 processor 和 handler </p>
 * <p>用 spring 多例的方式，通过 BeanFactory 来实例化 processor 和 handler</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Configuration
public class DemoConfiguration {
    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public DataService config() {

        TestEventProcessor testEventProcessor = beanFactory.getBean(TestEventProcessor.class);
        Yhandler yhandler = beanFactory.getBean(Yhandler.class);
        Xhandler xhandler = beanFactory.getBean(Xhandler.class);

        // 初始化 http processor
        List<String> hosts = new LinkedList<>();
        hosts.add("http://localhost:9909");
        hosts.add("http://127.0.0.1:9909");

        HttpClientEventProcessor.Config config = new HttpClientEventProcessor.Config();
        config.setHosts(hosts);

        HttpClientEventProcessor httpClientEventProcessor = beanFactory.getBean(HttpClientEventProcessor.class);
        httpClientEventProcessor.setConfig(config);

        //

        //@formatter:off
        return DataServiceBuilder.processors()
                // 构造测试processor
                .processor(testEventProcessor)
                    // 构造测试handler
                    .handler(xhandler)
                    .handler(yhandler)
                    .end()
                .processor(httpClientEventProcessor)
                    // 构造测试handler
                    .handler(new TestHttpEventHandler())
                    .end()
                .build();

        //@formatter:on
    }
}
