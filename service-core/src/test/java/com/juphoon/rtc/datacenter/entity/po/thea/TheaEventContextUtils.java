package com.juphoon.rtc.datacenter.entity.po.thea;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>模拟天赛全量数据上传事件工具类</p>
 * <p>TODO</p>
 *
 * @author Jiahui.Huang
 * @date 2022/7/12 11:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class TheaEventContextUtils {
    public static EventContext getEventContext(){
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };

        HashMap<String, Object> map = null;


        try {
            map = mapper.readValue("{\n" +
                    "        \"tkm_tessar\": \"TS2.0\",\n" +
                    "        \"tkm_collect_time\": 1656668977185,\n" +
                    "        \"tkm_domain_id\": \"100074\",\n" +
                    "        \"tkm_app_id\": \"2\",\n" +
                    "        \"tkm_access_type\": 1,\n" +
                    "        \"tkm_platform\": 6,\n" +
                    "        \"tkm_ip_addr\": \"192.168.9.99\",\n" +
                    "        \"tkm_sdk_ver\": \"ConfUtils@d4a9736(220630)-21.03.6435.48\",\n" +
                    "        \"tkm_os_ver\": \"3.10.0-693.el7.x86_64\",\n" +
                    "        \"tko_service\": \"\",\n" +
                    "        \"tko_account_id\": \"[username:delivery_JMDS.Main0.Main01.Main_0@100074.cloud.justalk.com]\",\n" +
                    "        \"tko_session_id\": \"<UnifiedSession>\",\n" +
                    "        \"tko_brand\": \"Unknown\",\n" +
                    "        \"tko_factory\": \"Unknown\",\n" +
                    "        \"tko_cpu_ver\": \"Intel(R) Xeon(R) CPU E5-2699C v4 @ 2.20GHz\",\n" +
                    "        \"tko_net_type\": \"\",\n" +
                    "        \"body\": {\n" +
                    "            \"general\": {\n" +
                    "                \"client_type\": 1,\n" +
                    "                \"rroom_id\": \"\",\n" +
                    "                \"m_id\": \"103451680701174926\",\n" +
                    "                \"join_cost\": 40,\n" +
                    "                \"conf_role\": 292\n" +
                    "            },\n" +
                    "            \"zmf\": {\n" +
                    "                \"cs_a_vol\": 100\n" +
                    "            },\n" +
                    "            \"jsm\": {\n" +
                    "                \"cb_account_id\": \"[username:delivery_JMDS.Main0.Main01.Main_0@100074.cloud.justalk.com]\",\n" +
                    "                \"su_bw\": 250,\n" +
                    "                \"su_br\": 0,\n" +
                    "                \"su_loss\": 0,\n" +
                    "                \"su_jitter\": 0,\n" +
                    "                \"su_rtt\": 0,\n" +
                    "                \"sd_bw\": 1000,\n" +
                    "                \"sd_br\": 809,\n" +
                    "                \"sd_loss\": 0,\n" +
                    "                \"sd_jitter\": 0,\n" +
                    "                \"ce_event_jsm\": [],\n" +
                    "                \"recv_actor\": [\n" +
                    "                    {\n" +
                    "                        \"cr_recv_actor_id\": 3,\n" +
                    "                        \"cb_account_id\": \"[username:agent3@100645.cloud.justalk.com]\",\n" +
                    "                        \"cr_a_mos\": 0,\n" +
                    "                        \"ce_event_aud\": [\n" +
                    "                            {\n" +
                    "                                \"event_code\": \"I121\",\n" +
                    "                                \"timestamp\": 1656668973754\n" +
                    "                            }\n" +
                    "                        ],\n" +
                    "                        \"cr_v_fps\": 0,\n" +
                    "                        \"cr_v_rend_fps\": 0,\n" +
                    "                        \"cr_v_br\": 0,\n" +
                    "                        \"cr_v_dec_time\": 0,\n" +
                    "                        \"cr_v_subs_w\": 952,\n" +
                    "                        \"cr_v_subs_h\": 528,\n" +
                    "                        \"ce_event_jsm\": [\n" +
                    "                            {\n" +
                    "                                \"event_code\": \"I210\",\n" +
                    "                                \"extend_info\": 3,\n" +
                    "                                \"timestamp\": 1656668972928\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"cr_recv_actor_id\": 1,\n" +
                    "                        \"cb_account_id\": \"[username:cat@100645.cloud.justalk.com]\",\n" +
                    "                        \"cr_a_mos\": 190,\n" +
                    "                        \"cr_a_br\": 42,\n" +
                    "                        \"ce_event_aud\": [\n" +
                    "                            {\n" +
                    "                                \"event_code\": \"I121\",\n" +
                    "                                \"timestamp\": 1656668972857\n" +
                    "                            }\n" +
                    "                        ],\n" +
                    "                        \"cr_v_w\": 952,\n" +
                    "                        \"cr_v_h\": 528,\n" +
                    "                        \"cr_v_fps\": 6,\n" +
                    "                        \"cr_v_rend_fps\": 8,\n" +
                    "                        \"cr_v_br\": 121,\n" +
                    "                        \"cr_v_dec_time\": 2,\n" +
                    "                        \"ce_event_vid\": [\n" +
                    "                            {\n" +
                    "                                \"event_code\": \"I215\",\n" +
                    "                                \"timestamp\": 1656668973009\n" +
                    "                            }\n" +
                    "                        ],\n" +
                    "                        \"cr_v_subs_w\": 952,\n" +
                    "                        \"cr_v_subs_h\": 528,\n" +
                    "                        \"ce_event_jsm\": [\n" +
                    "                            {\n" +
                    "                                \"event_code\": \"I210\",\n" +
                    "                                \"extend_info\": 3,\n" +
                    "                                \"timestamp\": 1656668972932\n" +
                    "                            }\n" +
                    "                        ]\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }", typeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        Map<String, Object> params = map;

        Event event = Event.builder()
                .domainId(100645)
                .appId(0)
                .type(900)
                .number(0)
                .timestamp(System.currentTimeMillis())
                .params(params)
                .uuid(UUID.randomUUID().toString())
                .build();

        EventContext ec = new EventContext(event);
        return ec;
    }
}
