package com.juphoon.rtc.datacenter.handle.database;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.entity.po.JrtcAcdCommonPo;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;

import java.util.Map;

/**
 * <p>数据库表handler的抽象类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 14:44
 */
@Slf4j
public abstract class AbstractDatabaseHandler extends AbstractEventHandler {

    @Override
    public boolean handle(EventContext ec) {
        JrtcAcdCommonPo commonPo = buildJrtcAcdCommonPo(ec.getEvent().getParams());
        JrtcAcdCommonPo localObj = selectByUnique(commonPo);
        if (null == localObj) {
            // 上锁，保证并发时 确定只有一条数据insert
            synchronized (this) {
                localObj = selectByUnique(commonPo);
                if (null == localObj) {
                    try {
                        insertSelective(commonPo);
                    } catch (DuplicateKeyException e) {
                        tryUpdate(commonPo);
                    }
                    return true;
                }
            }
        }
        updateByUniqueHashCode(localObj.getUniqueHashCode(), commonPo.getDuration(), commonPo.getCnt());
        return true;
    }

    protected void tryUpdate(JrtcAcdCommonPo commonPo) {
        JrtcAcdCommonPo localObj = selectByUnique(commonPo);
        if (null != localObj) {
            updateByUniqueHashCode(localObj.getUniqueHashCode(), commonPo.getDuration(), commonPo.getCnt());
        } else {
            log.warn("{}", commonPo.toString());
            throw new RuntimeException("insert failed, then update ,but not found data!!!");
        }
    }

    /**
     * 构建具体表的po
     * @param params
     * @return
     */
    abstract JrtcAcdCommonPo buildJrtcAcdCommonPo(Map<String, Object> params);

    /**
     * 查询确定要更新的那一条数据
     * @param commonPo
     * @return
     */
    abstract JrtcAcdCommonPo selectByUnique(JrtcAcdCommonPo commonPo);

    /**
     * 写入数据
     * @param commonPo
     * @return
     */
    abstract int insertSelective(JrtcAcdCommonPo commonPo);

    /**
     * 根据id更新值
     * @param uniqueHashCode
     * @param duration
     * @param cnt
     * @return
     */
    abstract int updateByUniqueHashCode(Integer uniqueHashCode, Long duration, Integer cnt);

}
