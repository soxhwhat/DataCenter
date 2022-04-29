package com.juphoon.rtc.datacenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdExtServiceLevelDailyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>jrtc_acd_ext_connect_daily表的mapper类</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/4/06 11:49
 */
@Component
@Mapper
public interface AcdExtServiceLevelDailyMapper extends BaseMapper<AcdExtServiceLevelDailyPO> {

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(AcdExtServiceLevelDailyPO record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    AcdExtServiceLevelDailyPO selectByPrimaryKey(Long id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(AcdExtServiceLevelDailyPO record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(AcdExtServiceLevelDailyPO record);

    /**
     * 根据uniqueKey更新次数
     * @param uniqueKey
     * @param cnt
     * @return
     */
    int updateAddValueByUniqueKey(@Param("uniqueKey") String uniqueKey,
                                  @Param("cnt") Integer cnt);

    /**
     * 根据uniqueKey查询
     *
     * @param uniqueKey
     * @return
     */
    AcdExtServiceLevelDailyPO selectByUniqueKey(@Param("uniqueKey") String uniqueKey);
}