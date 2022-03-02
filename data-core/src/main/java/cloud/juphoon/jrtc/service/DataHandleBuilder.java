package cloud.juphoon.jrtc.service;

import cloud.juphoon.jrtc.handler.IEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/2 11:01
 * @Description:
 */
@Component
@Slf4j
public class DataHandleBuilder {

    @Autowired
    ApplicationContext applicationContext;

    public <T extends IEventHandler> T newHandle(Class<T> clz) throws Exception {
        try {
            T eventHandler = clz.newInstance();
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                Autowired annotation = field.getAnnotation(Autowired.class);
                if (annotation != null){
                    Object bean = applicationContext.getBean(field.getType());
                    if (bean == null) {
                        throw new ClassNotFoundException("bean未在spring中找到");
                    }
                    field.setAccessible(true);
                    field.set(eventHandler,bean);
                    field.setAccessible(false);
                }
            }
            return eventHandler;
        } catch (ClassNotFoundException ce){
            log.error(ce.getMessage());
            throw ce;
        } catch (Exception e) {
            log.error("buildHandle失败:{}", clz.getName());
            throw e;
        }
    }
}
