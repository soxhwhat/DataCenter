package com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.juphoon.iron.component.utils.IronJsonUtils;

import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadVideoKeyFramePO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.VideoKeyFrameEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class VideoKeyFrameConvert {

    public final static String TYPE_KEY = "type";
    public final static String TYPE_TEXT = "text";
    public final static String TYPE_LINK = "link";
    //public final static String DATA_KEY = "data";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public List<UploadVideoKeyFramePO> convertPO(ArrayNode objectNode) {
        List<VideoKeyFrameEntity<?>> list = convert(objectNode);
        if (list == null) {
            return null;
        }
        return list.stream().map(this::convertPO).collect(Collectors.toList());

    }


    public VideoKeyFrameEntity<?> convert(UploadVideoKeyFramePO videoKeyFramePO) {
        VideoKeyFrameEntity videoKeyFrameEntity = new VideoKeyFrameEntity();
        videoKeyFrameEntity.setId(videoKeyFramePO.getId());
        videoKeyFrameEntity.setTimeOffset(videoKeyFramePO.getTimeOffset());
        videoKeyFrameEntity.setType(videoKeyFramePO.getKeyFrameType());
        videoKeyFrameEntity.setVideoId(videoKeyFramePO.getVideoId());
        videoKeyFrameEntity.setData(getKeyEntity(videoKeyFramePO.getKeyFrameType(), videoKeyFramePO.getKeyFrameData()));
        return videoKeyFrameEntity;

    }


    public UploadVideoKeyFramePO convertPO(VideoKeyFrameEntity videoKeyFrameEntity) {
        UploadVideoKeyFramePO videoKeyFramePO = new UploadVideoKeyFramePO();
        videoKeyFramePO.setId(videoKeyFrameEntity.getId());
        videoKeyFramePO.setVideoId(videoKeyFrameEntity.getVideoId());
        videoKeyFramePO.setTimeOffset(videoKeyFrameEntity.getTimeOffset());
        videoKeyFramePO.setKeyFrameType(videoKeyFrameEntity.getType());
        videoKeyFramePO.setKeyFrameData(IronJsonUtils.objectToJson(videoKeyFrameEntity.getData()));
        return videoKeyFramePO;
    }

    public List<VideoKeyFrameEntity<?>> convert(ArrayNode objectNode) {
        if (objectNode == null) {
            return Collections.emptyList();
        }
        List<VideoKeyFrameEntity<?>> list = new ArrayList<>();
        for (JsonNode jsonNode : objectNode) {
            VideoKeyFrameEntity<?> vkf = null;
            try {
                switch (jsonNode.get(TYPE_KEY).asText()) {
                    case TYPE_TEXT:
                        vkf = MAPPER.readValue(jsonNode.toString(), new TypeReference<VideoKeyFrameEntity<TextKeyFrameData>>() {
                        });
                        break;
                    case TYPE_LINK:
                        vkf = MAPPER.readValue(jsonNode.toString(), new TypeReference<VideoKeyFrameEntity<LinkKeyFrameData>>() {
                        });
                        break;
                    default:
                        break;
                }
                list.add(vkf);

            } catch (JsonProcessingException e) {
                log.warn("视频打点数据解析错误:{}", e);
            }
        }
        return list;
    }


    private KeyFrameData getKeyEntity(String type, String data) {
        KeyFrameData vkf = null;
        try {
            switch (type) {
                case TYPE_TEXT:
                    vkf = MAPPER.readValue(data, TextKeyFrameData.class);
                    break;
                case TYPE_LINK:
                    vkf = MAPPER.readValue(data, LinkKeyFrameData.class);
                    break;
                default:
                    break;
            }

        } catch (JsonProcessingException e) {
            log.warn("视频打点数据解析错误:{} type:{},data:{}", e, type, data);
        }
        return vkf;
    }

    public VideoKeyFrameEntity<?> convert(VideoKeyFrameDTO videoKeyFrameDTO) {
        VideoKeyFrameEntity<KeyFrameData> videoKeyFrameEntity = new VideoKeyFrameEntity<>();
        videoKeyFrameEntity.setTimeOffset(videoKeyFrameDTO.getTimeOffset());
        videoKeyFrameEntity.setType(videoKeyFrameDTO.getType());
        videoKeyFrameEntity.setVideoId(videoKeyFrameDTO.getVideoId());
        KeyFrameData v = getKeyEntity(videoKeyFrameDTO.getType(), IronJsonUtils.mapToJson(videoKeyFrameDTO.getData()));
        videoKeyFrameEntity.setData(v);
        return videoKeyFrameEntity;

    }
}
