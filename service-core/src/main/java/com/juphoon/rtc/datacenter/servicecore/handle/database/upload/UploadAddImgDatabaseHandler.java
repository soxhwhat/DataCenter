package com.juphoon.rtc.datacenter.servicecore.handle.database.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadImageInfoPO;
import com.juphoon.rtc.datacenter.servicecore.handle.database.AbstractDatabaseHandler;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadImageInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.INSERT_SUCCESS_IMAGE_PO;


/**
 * <p>埋点数据handler</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/5/10 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
public class UploadAddImgDatabaseHandler extends AbstractDatabaseHandler<UploadImageInfoPO> {

    @Autowired
    private UploadImageInfoMapper mapper;

    @Override
    public UploadImageInfoPO preData(Event event) {
        log.info("preData:{}", event.getParams());
        Map<String, Object> params = event.getParams();
        ObjectMapper objectMapper = new ObjectMapper();
        UploadImageInfoPO uploadImageInfoPO = objectMapper.convertValue(params, UploadImageInfoPO.class);
        log.info("UploadImageInfoPO:{}", uploadImageInfoPO);
        return uploadImageInfoPO;
    }

    @Override
    public Integer insertSelective(UploadImageInfoPO event) {
        return mapper.insert(event);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.UploadAddImgDatabaseHandler;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(
                INSERT_SUCCESS_IMAGE_PO
        );
    }
}
