package cloud.juphoon.jrtc.service;

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
public class DataSpringObjBuilder {

    @Autowired
    ApplicationContext applicationContext;

    public <T> T newObj(Class<?> clz, T clzObj) throws Exception {
        try {
            if (clzObj == null) {
                clzObj = (T) clz.newInstance();
            }
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                Autowired annotation = field.getAnnotation(Autowired.class);
                if (annotation != null) {
                    Object bean = applicationContext.getBean(field.getType());
                    if (bean == null) {
                        throw new ClassNotFoundException("bean未在spring中找到");
                    }
                    field.setAccessible(true);
                    field.set(clzObj, bean);
                    field.setAccessible(false);
                }
            }
            return clzObj;
        } catch (ClassNotFoundException ce) {
            log.error(ce.getMessage());
            throw ce;
        } catch (Exception e) {
            log.error("buildHandle失败:{}", clz.getName());
            throw e;
        }
    }

    public <T> T newObj(T clzObj) throws Exception {
        if (clzObj != null) {
            return (T) newObj(clzObj.getClass(), clzObj);
        } else {
            throw new IllegalArgumentException("clzObj为空");
        }
    }

    public <T> T newObj(Class<?> clz) throws Exception {
        return newObj(clz, null);
    }
}
