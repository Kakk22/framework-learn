package com.cyf.test;

import com.alibaba.fastjson.JSON;
import com.cyf.domain.Bank;
import com.cyf.repository.BankRepository;
import com.cyf.service.BankService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 陈一锋
 * @date 2022/9/25 10:38 上午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BankTest {

    @Resource
    private BankService bankService;
    @Resource
    private BankRepository bankRepository;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    //-------新增文档------------------------
    @Test
    public void test_save(){
        Bank bank = new Bank();
        bank.setId(100005L);
        bank.setBalance(new BigDecimal("20005"));
        bank.setFirstname("Stack2");
        bank.setLastname("Jone");
        bank.setTime(LocalDateTime.now());
        bank.setCnDesc("中文描述 用于ik分词器20005");
        elasticsearchRestTemplate.save(bank);

        Optional<Bank> queryBank = bankRepository.findById(100005L);
        System.out.println("---");
    }

    //----------------删除----------------------------
    @Test
    public void test_delete(){
        Bank bank = new Bank();
        bank.setId(100001L);
        bank.setBalance(new BigDecimal("2000"));
        bank.setFirstname("Stack");
        bank.setLastname("Jone");
        String delete = elasticsearchRestTemplate.delete(bank);

        System.out.println(delete);
    }



    //------------------查询------------------------------

    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Test
    public void test_search() throws IOException {
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("balance")
                .gte("10000")
                .lte("20000");
        BoolQueryBuilder builder = new BoolQueryBuilder()
                .must(rangeQueryBuilder);

        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(builder);
        System.out.println("DSL:"+nativeSearchQuery.getQuery());

        SearchRequest searchRequest = new SearchRequest("bank");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(nativeSearchQuery.getQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] searchHits = searchResponse.getHits().getHits();

        List<Bank> list = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            String data = searchHit.getSourceAsString();
            list.add(JSON.parseObject(data,Bank.class));
        }

        System.out.println(list);

        Page<Bank> search = bankRepository.search(nativeSearchQuery);
        System.out.println("----");
    }



    @Test
    public void test_find_state(){
        List<Bank> banks = bankService.findByState("ID");
        System.out.println("-----");
    }

    @Test
    public void test_find_state_page(){
        PageRequest page = PageRequest.of(0, 10);
        Page<Bank> banks = bankRepository.findBanksByState("ID",page);
        System.out.println("-----"+banks);
    }

    @Test
    public void test_find_state_and_address_lick(){
        List<Bank> banks = bankRepository.findBanksByStateAndAddressLike("ID","Place");
        System.out.println("-----");
    }
}
