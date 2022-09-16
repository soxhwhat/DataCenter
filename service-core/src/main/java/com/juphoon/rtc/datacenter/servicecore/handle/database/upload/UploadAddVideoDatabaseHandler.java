package com.juphoon.rtc.datacenter.servicecore.handle.database.upload;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.entity.bo.UploadRecordInfoBO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadRecordInfoPO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadVideoKeyFramePO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.VideoKeyFrameEntity;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.VideoKeyFrameBO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.VideoKeyFrameConvert;
import com.juphoon.rtc.datacenter.servicecore.handle.database.AbstractDatabaseHandler;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadRecordInfoMapper;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadVideoKeyFrameMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
public class UploadAddVideoDatabaseHandler extends AbstractDatabaseHandler<UploadRecordInfoBO> {

    private static final String VIDEO_KEY_FRAME_LIST = "videoKeyFrameList";

    private static final String TIME_OFFSET = "timeOffset";
    private static final String TYPE = "type";
    private static final String DATA = "data";


    @Autowired
    private UploadRecordInfoMapper uploadRecordInfoMapper;

    @Autowired
    private UploadVideoKeyFrameMapper uploadVideoKeyFrameMapper;

    @Autowired
    VideoKeyFrameConvert videoKeyFrameConvert;

    @Override
    public UploadRecordInfoBO preData(Event event) {
        log.info("preData:{}", event.getParams());
        Map<String, Object> params = event.getParams();
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, Object>> videoKeyFrameList = (List<HashMap<String, Object>>) params.get(VIDEO_KEY_FRAME_LIST);
        params.remove(VIDEO_KEY_FRAME_LIST);
        UploadRecordInfoBO uploadRecordInfoPO = objectMapper.convertValue(params, UploadRecordInfoBO.class);
        setVideoKeyFrameList(videoKeyFrameList, uploadRecordInfoPO);
        log.info("UploadRecordInfoPO:{}", uploadRecordInfoPO);
        return uploadRecordInfoPO;
    }


    void setVideoKeyFrameList(List<HashMap<String, Object>> videoKeyFrameList, UploadRecordInfoBO uploadRecordInfoPO) {
        try {
            List<VideoKeyFrameBO> list = null;
            if (videoKeyFrameList != null && videoKeyFrameList.size() > 0) {
                list = new ArrayList<>();
                for (HashMap<String, Object> map : videoKeyFrameList) {
                    VideoKeyFrameBO videoKeyFrameBO = convertPO(map);
                    list.add(videoKeyFrameBO);
                }
                uploadRecordInfoPO.setVideoKeyFrameBOList(list);
            }
        } catch (Exception e) {
            log.error("打点信息解析异常{}", e);
        }

    }

    public VideoKeyFrameBO convertPO(HashMap<String, Object> map) {
        VideoKeyFrameBO videoKeyFramePO = new VideoKeyFrameBO();
        videoKeyFramePO.setTimeOffset(Long.parseLong(String.valueOf(map.getOrDefault(TIME_OFFSET, 0))));
        videoKeyFramePO.setKeyFrameType(String.valueOf(map.get(TYPE)));
        videoKeyFramePO.setKeyFrameData(IronJsonUtils.objectToJson(map.get(DATA)));
        return videoKeyFramePO;
    }

    @Override
    public Integer insertSelective(UploadRecordInfoBO event) {
        int insert = uploadRecordInfoMapper.insert(event);
        List<VideoKeyFrameBO> videoKeyFrameBOList = event.getVideoKeyFrameBOList();
        UploadVideoKeyFramePO uploadVideoKeyFramePO = null;
        if (!CollectionUtils.isEmpty(videoKeyFrameBOList)) {
            for (VideoKeyFrameBO videoKeyFrameEntity : videoKeyFrameBOList) {
                videoKeyFrameEntity.setVideoId(event.getId());
                uploadVideoKeyFramePO = new UploadVideoKeyFramePO();
                BeanUtils.copyProperties(videoKeyFrameEntity, uploadVideoKeyFramePO);
                uploadVideoKeyFrameMapper.insert(uploadVideoKeyFramePO);
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
