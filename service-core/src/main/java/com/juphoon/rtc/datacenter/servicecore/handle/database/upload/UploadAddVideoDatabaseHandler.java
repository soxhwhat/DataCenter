package com.juphoon.rtc.datacenter.servicecore.handle.database.upload;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadRecordInfoPO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.VideoKeyFrameEntity;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.VideoKeyFrameConvert;
import com.juphoon.rtc.datacenter.servicecore.handle.database.AbstractDatabaseHandler;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadRecordInfoMapper;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadVideoKeyFrameMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.INSERT_SUCCESS_VIDEO_PO;


/**
 * <p>埋点数据handler</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/5/10 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
public class UploadAddVideoDatabaseHandler extends AbstractDatabaseHandler<UploadRecordInfoPO> {

    @Autowired
    private UploadRecordInfoMapper uploadRecordInfoMapper;

    @Autowired
    private UploadVideoKeyFrameMapper uploadVideoKeyFrameMapper;

    @Autowired
    VideoKeyFrameConvert videoKeyFrameConvert;

    @Override
    public UploadRecordInfoPO preData(Event event) {
        log.info("preData:{}", event.getParams());
        Map<String, Object> params = event.getParams();
        ObjectMapper objectMapper = new ObjectMapper();
        UploadRecordInfoPO uploadRecordInfoPO = objectMapper.convertValue(params, UploadRecordInfoPO.class);
        log.info("UploadRecordInfoPO:{}", uploadRecordInfoPO);
        return uploadRecordInfoPO;
    }

    @Override
    public Integer insertSelective(UploadRecordInfoPO event) {
        int insert = uploadRecordInfoMapper.insert(event);
        List<VideoKeyFrameEntity<?>> videoKeyFrameList = event.getVideoKeyFrameList();
        if (!CollectionUtils.isEmpty(videoKeyFrameList)) {
            for (VideoKeyFrameEntity<?> videoKeyFrameEntity : videoKeyFrameList) {
                videoKeyFrameEntity.setVideoId(event.getId());
                uploadVideoKeyFrameMapper.insert(videoKeyFrameConvert.convertPO(videoKeyFrameEntity));
            }
        }
        return insert;
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.UploadAddVideoDatabaseHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                INSERT_SUCCESS_VIDEO_PO
        );
    }
}
