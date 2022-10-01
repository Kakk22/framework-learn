package com.cyf.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 陈一锋
 * @date 2022/9/29 9:56 下午
 */
@Data
@Document(indexName = "bank")
public class Good implements Serializable {

    private static final long serialVersionUID = -5691876815008754326L;

    /**
     * 商品编号
     */
    private Long id;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品销售数量
     */
    private Integer saleNum;

    /**
     * 商品分类
     */
    private String categoryName;

    /**
     * 商品品牌
     */
    private String brandName;

    private String spec;
    /**
     * 上下架状态
     */
    private Integer status;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
