package com.juphoon.rtc.datacenter.dist.b03.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juphoon.rtc.datacenter.dist.b03.entity.MdEventPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/5/10 14:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Repository
@Mapper
@DS("ods")
public interface MdEventMapper extends BaseMapper<MdEventPO> {



}
