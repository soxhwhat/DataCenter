package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.DataCenterApplication;
import cloud.juphoon.jrtc.handler.test.Ahandler;
import cloud.juphoon.jrtc.service.DataSpringObjBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/2 12:44
 * @Description:
 */
@SpringBootTest(classes = DataCenterApplication.class)
public class DataSpringObjBuilderTest {

    @Autowired
    DataSpringObjBuilder dataSpringObjBuilder;

    @Test
    public void buildTest(){
        try {
            Ahandler ahandler = dataSpringObjBuilder.newObj(Ahandler.class);
            System.out.println(ahandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
