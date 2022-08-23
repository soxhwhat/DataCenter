package test.com.juphoon.rtc.datacenter;

import com.juphoon.iron.cube.starter.rccode.ServiceEvent;
import com.juphoon.rtc.def.domain.DomainCodeEnum;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 产研 errorcode.csv 文件生成用例
 *
 * 集成步骤：
 * 1. 指定异常码定义文件
 * 2. 指定服务名称
 *
 */
public class EventCodeFileGenerator {
    /**
     * ** 确保 errorcode.csv 输出在项目根目录下 **
     * ** 确保 errorcode.csv 输出在项目根目录下 **
     * ** 确保 errorcode.csv 输出在项目根目录下 **
     */
    private final static String PATH = System.getProperty("user.dir") + "/../errorcode.csv";

    /**
     * 步骤 1 : 指定异常码定义文件
     */
    private final static String[] SERVICE_EVENT_CLASSES = new String[]{
            "com.juphoon.iron.cube.starter.rccode.CommonServiceEventConstants",
            "com.juphoon.rtc.datacenter.datacore.JrtcDataCenterEventCode"
    };

    /**
     * 步骤 2 : 指定领域编码
     */
    private final static DomainCodeEnum DOMAIN_CODE = DomainCodeEnum.DATA_CENTER;

    /**
     * 内部类，用于输出
     */
    static class ErrorCodeInfo {
        String errorCode;
        String errorName;
        String description;
    }

    @Test
    public void generateCsvFile() throws IllegalAccessException, ClassNotFoundException, IOException {
        List<ErrorCodeInfo> errorCodeInfos = scanError();
        generateFile(errorCodeInfos);
    }

    private List<ErrorCodeInfo> scanError() throws ClassNotFoundException, IllegalAccessException {
        List<ErrorCodeInfo> errorCodeInfos = new ArrayList<>();
        for (String serviceEventClass : SERVICE_EVENT_CLASSES) {
            Class<?> clazz = Class.forName(serviceEventClass);
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.getType().getName().contains("ServiceEvent")) {
                    field.setAccessible(true);
                    ServiceEvent serviceEvent = (ServiceEvent) field.get(clazz);
//                    if (!serviceEvent.getLevel().equals(Level.ERROR)) {
//                        continue;
//                    }
                    ErrorCodeInfo errorCodeInfo = new ErrorCodeInfo();
                    errorCodeInfo.errorCode = String.valueOf(serviceEvent.getEventCode());
                    errorCodeInfo.errorName = field.getName();
                    errorCodeInfo.description = serviceEvent.getRootCauseReason();
                    errorCodeInfos.add(errorCodeInfo);
                }
            }
        }

        return errorCodeInfos;
    }

    private void generateFile(List<ErrorCodeInfo> errorCodeInfos) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("领域名称,错误码,错误定义,错误说明");

        for (ErrorCodeInfo errorCodeInfo : errorCodeInfos) {
            String line = String.join(", ", DOMAIN_CODE.getServiceName(), errorCodeInfo.errorCode, errorCodeInfo.errorName, errorCodeInfo.description);
            sb.append("\r\n").append(line);
        }

        String content = sb.toString();
        Path path = Paths.get(PATH);

        if (Files.exists(path)) {
            Files.delete(path);
        }

        Files.createFile(path);
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }


}
