package com.juphoon.rtc.datacenter.servicecore.handle.database.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadRecordErrorInfoPO;
import com.juphoon.rtc.datacenter.servicecore.handle.database.AbstractDatabaseHandler;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadRecordErrorInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.INSERT_ERROR_VIDEO_PO;


/**
 * <p>埋点数据handler</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/5/10 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
public class UploadAddErrorVideoDatabaseHandler extends AbstractDatabaseHandler<UploadRecordErrorInfoPO> {

    @Autowired
    private UploadRecordErrorInfoMapper mapper;

    @Override
    public UploadRecordErrorInfoPO preData(Event event) {
        log.info("preData:{}", event.getParams());
        Map<String, Object> params = event.getParams();
        ObjectMapper objectMapper = new ObjectMapper();
        UploadRecordErrorInfoPO uploadRecordErrorInfoPO = objectMapper.convertValue(params, UploadRecordErrorInfoPO.class);
        log.info("UploadRecordErrorInfoPO:{}", uploadRecordErrorInfoPO);
        return uploadRecordErrorInfoPO;
    }

    @Override
    public Integer insertSelective(UploadRecordErrorInfoPO event) {
        return mapper.insert(event);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.UploadAddErrorVideoDatabaseHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                INSERT_ERROR_VIDEO_PO
        );
    }
}
