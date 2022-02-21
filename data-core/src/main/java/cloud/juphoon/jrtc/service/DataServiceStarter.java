package cloud.juphoon.jrtc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 启动器
 *
 * @author ajian.zheng@juphoon.com
 * @date 2/18/22 10:39 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
public class DataServiceStarter {
    @Autowired
    private DataService dataService;

    @PostConstruct
    public void init() {
        dataService.start();
    }
}
