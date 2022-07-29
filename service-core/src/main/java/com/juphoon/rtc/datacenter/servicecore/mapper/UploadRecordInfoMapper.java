/**
 * Copyright (C), 2005-2019, Juphoon Corporation
 * <p>
 * FileName   : FileInfoMapper
 * Author     : wenqiangdong
 * Date       : 2019-09-12 09:53
 * Description:
 * <p>
 * <p>
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.juphoon.rtc.datacenter.servicecore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadRecordInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>音视频表mapper</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/7/28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Repository
@Mapper
public interface UploadRecordInfoMapper extends BaseMapper<UploadRecordInfoPO> {


}
