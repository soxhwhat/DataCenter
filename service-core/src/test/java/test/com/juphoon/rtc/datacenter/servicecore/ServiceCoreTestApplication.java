package test.com.juphoon.rtc.datacenter.servicecore;

import com.juphoon.iron.cube.starter.annotation.CubeStarterApplication;
import com.juphoon.rtc.def.domain.DomainCodeEnum;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * <p>示例工程</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/15/22 6:01 PM
 */
@SpringBootApplication(scanBasePackages = "com.juphoon.rtc.datacenter", exclude = DataSourceAutoConfiguration.class)
@CubeStarterApplication(domainCode = DomainCodeEnum.DATA_CENTER)
@MapperScan(basePackages = "com.juphoon.rtc.datacenter")
@EnableMongoRepositories(basePackages = "com.juphoon.rtc.datacenter")
public class ServiceCoreTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCoreTestApplication.class, args);
    }
}
