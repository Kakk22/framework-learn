package com.cyf.service;

import com.cyf.domain.Bank;
import com.cyf.repository.BankRepository;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈一锋
 * @date 2022/9/25 10:36 上午
 */
@Service
public class BankService {

    @Resource
    private BankRepository bankRepository;

    public List<Bank> findByState(String state) {
        return bankRepository.findBanksByState(state);
    }

    public void query(){
//        new Querybuilder`
//        new NativeSearchQuery()
    }
}
