package com.cyf.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyf.domain.Good;
import com.cyf.repository.GoodsRepository;
import lombok.SneakyThrows;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈一锋
 * @date 2022/9/29 10:05 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodTest {


    @Resource
    private GoodsRepository goodsRepository;
    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test_query_all() {
        List<Good> goods = goodsRepository.selectAll();
        for (Good good : goods) {
            System.out.println(JSON.toJSON(good));
        }
        System.out.println("--");
    }

    @Test
    public void import_to_es() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        List<Good> goods = goodsRepository.selectAll();
        for (Good good : goods) {
            bulkRequest.add(new IndexRequest("goods")
                    .id(good.getId().toString())
                    .source((JSONObject) JSON.toJSON(good)));
        }

        BulkResponse responses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("数据导入es成功");
    }

    @SneakyThrows
    @Test
    public void test_select_doc() {
        GetRequest request = new GetRequest("goods", "536563");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }


    /**
     * term查询：不会分析查询条件，只有当词条和查询字符串完全匹配时才匹配，也就是精确查找
     */
    @Test
    public void test_term_query() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("title", "华为"));

        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.source(searchSourceBuilder);

        System.out.println("DSL:" + searchRequest.source());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Good> res = new ArrayList<>();
        if (response.status().equals(RestStatus.OK)) {
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), Good.class));
            }
        }

        System.out.println("查询结果对象:" + JSON.toJSONString(res));
    }

    @Test
    public void test_terms_query() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termsQuery("title", "华为", "三星", "TCL"));

        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.source(searchSourceBuilder);

        System.out.println("DSL:" + searchRequest.source());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Good> res = new ArrayList<>();
        if (response.status().equals(RestStatus.OK)) {
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), Good.class));
            }
        }

        System.out.println("查询结果对象:" + JSON.toJSONString(res));
    }


    /**
     * match 全文查询, 全文查询会分析查询条件，先将查询条件进行分词，然后查询，求并集
     */
    @Test
    public void test_match_all_query() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        searchSourceBuilder.sort("price", SortOrder.ASC);
        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.source(searchSourceBuilder);

        System.out.println("DSL:" + searchRequest.source());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Good> res = new ArrayList<>();
        if (response.status().equals(RestStatus.OK)) {
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), Good.class));
            }
        }

        System.out.println("查询结果对象:" + JSON.toJSONString(res));
    }

    /**
     * 匹配查询数据
     */
    @Test
    public void test_match_query() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "三星"));

        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.source(searchSourceBuilder);

        System.out.println("DSL:" + searchRequest.source());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Good> res = new ArrayList<>();
        if (response.status().equals(RestStatus.OK)) {
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), Good.class));
            }
            System.out.println("数据数量:" + hits.getHits().length);
        }

        System.out.println("查询结果对象:" + JSON.toJSONString(res));
    }


    /**
     * 词语匹配查询数据
     */
    @Test
    public void test_match_phrase_query() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("title", "三星"));

        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.source(searchSourceBuilder);

        System.out.println("DSL:" + searchRequest.source());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Good> res = new ArrayList<>();
        if (response.status().equals(RestStatus.OK)) {
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), Good.class));
            }
            System.out.println("数据数量:" + hits.getHits().length);
        }

        System.out.println("查询结果对象:" + JSON.toJSONString(res));
    }


    /**
     * 范围查询数据
     */
    @Test
    public void test_range_query() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("price").gte(1000).lte(2000));

        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.source(searchSourceBuilder);

        System.out.println("DSL:" + searchRequest.source());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Good> res = new ArrayList<>();
        if (response.status().equals(RestStatus.OK)) {
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), Good.class));
            }
            System.out.println("数据数量:" + hits.getHits().length);
        }

        System.out.println("查询结果对象:" + JSON.toJSONString(res));
    }

    //-----------------------------bool查询----------------------------

    /**
     * 查询标题包含三星
     * 且时间在2018-2022
     */
    @Test
    public void test_bool_query_01() throws IOException {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                .must(QueryBuilders.matchQuery("title", "三星"))
                .filter(QueryBuilders.rangeQuery("createTime").format("yyyy").gte("2018").lte("2022"));

        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.source(new SearchSourceBuilder().query(boolQueryBuilder));

        System.out.println("DSL:" + searchRequest.source());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Good> res = new ArrayList<>();
        if (response.status().equals(RestStatus.OK)) {
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), Good.class));
            }
            System.out.println("数据数量:" + hits.getHits().length);
        }

        System.out.println("查询结果对象:" + JSON.toJSONString(res));
    }



    /**
     * 查询包含必须包含 华为手机 词语的商品信息
     */
    @Test
    public void test_query_string() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(new QueryStringQueryBuilder("华为手机").defaultOperator(Operator.AND));

        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.source(searchSourceBuilder);

        System.out.println("DSL:" + searchRequest.source());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Good> res = new ArrayList<>();
        if (response.status().equals(RestStatus.OK)) {
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), Good.class));
            }
            System.out.println("数据数量:" + hits.getHits().length);
        }

        System.out.println("查询结果对象:" + JSON.toJSONString(res));
    }
}
