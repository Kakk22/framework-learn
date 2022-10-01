package com.cyf.repository;

import com.cyf.domain.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author 陈一锋
 * @date 2022/9/25 10:29 上午
 */
public interface BankRepository extends ElasticsearchRepository<Bank, Long> {


    /**
     * 通过单个字段查询
     */
    List<Bank> findBanksByState(String state);

    Page<Bank> findBanksByState(String state, Pageable pageable);

    List<Bank> findBanksByStateAndAddressLike(String state, String address);

}
