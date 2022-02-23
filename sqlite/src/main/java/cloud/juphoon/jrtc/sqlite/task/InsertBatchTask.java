package cloud.juphoon.jrtc.sqlite.task;

import cloud.juphoon.jrtc.sqlite.entity.Account;
import cloud.juphoon.jrtc.sqlite.service.SqlService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class InsertBatchTask implements Callable<Object> {

    List<Integer> is;
    SqlService sqlService;

    public InsertBatchTask(List<Integer> is, SqlService sqlService) {
        this.is = is;
        this.sqlService = sqlService;
    }

    @Override
    public Object call() {
        try {
            List<Account> list = new ArrayList<>();
            for (Integer i : is) {
                Account account = new Account();
                account.setId(i);
                account.setAccount(i);
                list.add(account);
            }
            log.info("insert begin--------:{}", is);
            sqlService.insertBatch(list);
            log.info("insert end--------:{}", is);
        } catch (Exception e) {
            log.error("insert error-------{}", is);
            e.printStackTrace();
        }

        return null;
    }
}