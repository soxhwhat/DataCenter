package com.juphoon.rtc.datacenter.entity.po.cube;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 *
 * infoBO
 * @author zhiwei.zhai
 * @date 2022.05.31
 */
@Data
@Document
public class InfoBO {

    @Id
    private String id;

    private Long timestamp;
    private String params;

    private String type;
    private List<String> tags;


}
