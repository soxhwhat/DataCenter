package cloud.juphoon.jrtc.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/1 14:05
 * @Description:
 */
@Component("SpringBeanUtils")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    public static Object getBean(String name) throws BeansException {

        return applicationContext.getBean(name);
    }
}