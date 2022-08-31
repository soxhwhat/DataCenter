package test.com.juphoon.rtc.datacenter.servicecore.handler.database.acdstat;

import com.juphoon.rtc.datacenter.servicecore.api.ServiceLevelTypeEnum;
import com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat.AcdExtServiceLevelDailyHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>日服务水平handlertest</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/7/12
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test-mysql")
public class AcdExtServiceLevelDailyHandlerTest {

    @Autowired
    private AcdExtServiceLevelDailyHandler acdExtServiceLevelDailyHandler;

    @Test
    public void testServiceLevelTypeEnums() {
        List<ServiceLevelTypeEnum> serviceLevelTypeEnums = acdExtServiceLevelDailyHandler.getServiceLevelTypeEnums();
        Assert.notEmpty(serviceLevelTypeEnums, "服务水平为空");
        Assert.isTrue(serviceLevelTypeEnums.get(0).equals(ServiceLevelTypeEnum.SERVICE_LEVEL_20SEC),
                ServiceLevelTypeEnum.SERVICE_LEVEL_20SEC.name());
        Assert.isTrue(serviceLevelTypeEnums.get(1).equals(ServiceLevelTypeEnum.SERVICE_LEVEL_30SEC),
                ServiceLevelTypeEnum.SERVICE_LEVEL_30SEC.name());
    }
}
