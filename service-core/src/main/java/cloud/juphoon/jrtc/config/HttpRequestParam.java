/**
 * Copyright (C), 2005-2019, Juphoon Corporation
 * <p>
 * FileName   : NoticeProperties
 * Author     : wenqiangdong
 * Date       : 2019-12-16 17:55
 * Description:
 * <p>
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cloud.juphoon.jrtc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>封装请求参数以及父类需要的对象</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/8
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
public class HttpRequestParam<T, D> {

    /**
     * 调用地址
     */
    private String host = "http://localhost:8089";

    private String endPoint = "";

    private T params;

    private D template;

}
