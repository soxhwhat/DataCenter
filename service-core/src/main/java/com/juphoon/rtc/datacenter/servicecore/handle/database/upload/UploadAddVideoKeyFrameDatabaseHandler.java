package com.juphoon.rtc.datacenter.servicecore.handle.database.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadVideoKeyFramePO;
import com.juphoon.rtc.datacenter.servicecore.handle.database.AbstractDatabaseHandler;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadVideoKeyFrameMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.INSERT_VIDEO_KEY_FRAME_PO;


/**
 * <p>埋点数据handler</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/5/10 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
public class UploadAddVideoKeyFrameDatabaseHandler extends AbstractDatabaseHandler<UploadVideoKeyFramePO> {

    @Autowired
    private UploadVideoKeyFrameMapper mapper;

    @Override
    public UploadVideoKeyFramePO preData(Event event) {
        log.info("preData:{}", event.getParams());
        Map<String, Object> params = event.getParams();
        ObjectMapper objectMapper = new ObjectMapper();
        UploadVideoKeyFramePO uploadVideoKeyFramePO = objectMapper.convertValue(params, UploadVideoKeyFramePO.class);
        log.info("UploadVideoKeyFramePO:{}", uploadVideoKeyFramePO);
        return uploadVideoKeyFramePO;
    }

    @Override
    public Integer insertSelective(UploadVideoKeyFramePO event) {
        return mapper.insert(event);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.UploadAddVideoKeyFrameDatabaseHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                INSERT_VIDEO_KEY_FRAME_PO
        );
    }
}
