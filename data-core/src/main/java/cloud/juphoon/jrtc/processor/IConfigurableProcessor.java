package cloud.juphoon.jrtc.processor;/**
 * <p>获取processor相关信息接口</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ke.wang@juphoon.com
 * @date    2022/3/9 15:07
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */ 
public interface IConfigurableProcessor<T,C> {

    /**
     * 获取template对象 例如mongoTemplate,restTemplate,xxMapper等等
     * @return T
     */
    T getTemplate(C c);

    /**
     * 获取相关配置信息
     * @return C
     */
    C getConfig();
}
