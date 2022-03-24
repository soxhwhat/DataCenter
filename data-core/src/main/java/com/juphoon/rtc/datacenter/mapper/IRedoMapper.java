package com.juphoon.rtc.datacenter.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juphoon.rtc.datacenter.api.RedoPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author zhiwei.zhai
 * @date 2022.03.16
 */
@Mapper
@DS("log")
public interface IRedoMapper extends BaseMapper<RedoPO> {


    /**
     * 初始化表
     */
    void initHandleDataTable();

    /**
     * 根据eventId
     * @param eventId
     */
    default List<RedoPO> selectByEventId(String eventId){
        LambdaQueryWrapper<RedoPO> queryWrapper = new QueryWrapper<RedoPO>().lambda();
        queryWrapper.eq(RedoPO::getEventId,eventId);
        return this.selectList(queryWrapper);
    };
}
