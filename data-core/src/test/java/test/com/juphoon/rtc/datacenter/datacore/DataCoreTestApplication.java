package test.com.juphoon.rtc.datacenter.datacore;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import com.juphoon.rtc.def.domain.DomainCodeEnum;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <p>示例工程</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 */
@SpringBootApplication(scanBasePackages = "com.juphoon", exclude = DataSourceAutoConfiguration.class)
@CubeStarterApplication(domainCode = DomainCodeEnum.DATA_CENTER)
@MapperScan(basePackages = "com.juphoon.rtc.datacenter")
public class DataCoreTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataCoreTestApplication.class, args);
    }
}
