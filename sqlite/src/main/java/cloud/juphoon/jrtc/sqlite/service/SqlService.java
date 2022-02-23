package cloud.juphoon.jrtc.sqlite.service;

import cloud.juphoon.jrtc.sqlite.entity.Account;
import cloud.juphoon.jrtc.sqlite.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/21 21:53
 * @Description:
 */
@Service
public class SqlService {

    @Autowired
    private AccountMapper accountMapper;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insert(Account account){
        accountMapper.insert(account);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insertBatch(List<Account> accounts){
        accountMapper.insertBatch(accounts);
    }
}
