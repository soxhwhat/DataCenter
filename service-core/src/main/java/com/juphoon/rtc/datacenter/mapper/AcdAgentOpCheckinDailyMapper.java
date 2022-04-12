package com.juphoon.rtc.datacenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdAgentOpCheckinDailyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>jrtc_acd_agentop_checkin_daily表的mapper类</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Mapper
@SuppressWarnings("PMD")
public interface AcdAgentOpCheckinDailyMapper extends BaseMapper<AcdAgentOpCheckinDailyPO> {
    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(AcdAgentOpCheckinDailyPO record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    AcdAgentOpCheckinDailyPO selectByPrimaryKey(Long id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(AcdAgentOpCheckinDailyPO record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(AcdAgentOpCheckinDailyPO record);

    /**
     * 根据uniqueKey更新最后登录登出时间
     * @param uniqueKey
     * @param lastCheckin
     * @param lastCheckout
     * @return
     */
    int updateLastCheckByUniqueKey(@Param("uniqueKey") String uniqueKey,
                                   @Param("lastCheckin") Long lastCheckin,
                                   @Param("lastCheckout") Long lastCheckout);

    /**
     * 根据uniqueKey更新首次登录时间
     * @param uniqueKey
     * @param firstCheckin
     * @param lastCheckin
     * @return
     */
    int updateFirstCheckByUniqueKey(@Param("uniqueKey") String uniqueKey,
                                   @Param("firstCheckin") Long firstCheckin,
                                   @Param("lastCheckin") Long lastCheckin);

    /**
     * 根据uniqueKey查询
     *
     * @param uniqueKey
     * @return
     */
    AcdAgentOpCheckinDailyPO selectByUniqueKey(@Param("uniqueKey") String uniqueKey);

    /**
     * 根据shift和agentId查询
     *
     * @param shift
     * @param agentId
     * @return
     */
    AcdAgentOpCheckinDailyPO selectByShiftAndAgentId(@Param("shift") String shift, @Param("agentId") String agentId);
}