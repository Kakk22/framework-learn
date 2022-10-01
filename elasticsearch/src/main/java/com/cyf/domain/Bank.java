package com.cyf.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 陈一锋
 * @date 2022/9/25 10:19 上午
 */
@Data
@Document(indexName = "bank")
public class Bank implements Serializable {

    private static final long serialVersionUID = 8217435755973630634L;

    @Id
    private Long id;
    @Field(name = "account_number")
    private Integer accountNumber;
    private BigDecimal balance;
    private String firstname;
    private String lastname;
    private Integer age;
    private String gender;
    private String address;
    private String employer;
    private String email;
    @Field(type = FieldType.Text)
    private String city;
    @Field(type = FieldType.Keyword)
    private String state;
    @Field(type = FieldType.Date,format = DateFormat.date_hour_minute_second)
    private LocalDateTime time;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String cnDesc;


}
