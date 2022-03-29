package com.juphoon.rtc.datacenter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>公共mapper</p>
 * <p>实现根据id统一更新次数或者时长</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 14:51
 */
@Mapper
@SuppressWarnings("PMD")
public interface AcdCommonMapper {
    int updateAddValueByUniqueKey(@Param("tableName") String tableName, @Param("uniqueKey") String uniqueKey,
                                  @Param("duration") Long duration,
                                  @Param("cnt") Integer cnt);
}
