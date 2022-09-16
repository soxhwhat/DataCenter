package com.juphoon.rtc.datacenter.servicecore.entity.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadRecordInfoPO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.VideoKeyFrameBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/9/16 10:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadRecordInfoBO extends UploadRecordInfoPO {

    private List<VideoKeyFrameBO> videoKeyFrameBOList;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
