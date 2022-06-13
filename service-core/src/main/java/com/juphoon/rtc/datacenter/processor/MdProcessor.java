//package com.juphoon.rtc.datacenter.processor;
//
//import com.juphoon.rtc.datacenter.api.EventContext;
//import com.juphoon.rtc.datacenter.api.ProcessorId;
//import com.juphoon.rtc.datacenter.binlog.ILogService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;
//import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_RELIABLE;
//
///**
// * <p>在开始处详细描述该类的作用</p>
// * <p>描述请遵循 javadoc 规范</p>
// * <p>TODO</p>
// *
// * @author ke.wang@juphoon.com
// * @date 2022/5/12 10:45
// * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
// */
//@Component
//public class MdProcessor extends AbstractEventProcessor {
//    @Autowired
//    @Qualifier(EVENT_BIN_LOG_IMPL_FLASH)
//    private ILogService<EventContext> eventLogService;
//
//    @Override
//    public ILogService<EventContext> logService() {
//        return eventLogService;
//    }
//
//    @Override
//    public ProcessorId processorId() {
//        return ProcessorId.MD;
//    }
//}
