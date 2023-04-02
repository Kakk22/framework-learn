package com.cyf.vo;

import com.cyf.model.Order;
import com.cyf.model.User;

import java.util.List;

/**
 * @author 陈一锋
 * @date 2021/8/22 10:02 下午
 */
public class UserVo extends User {

    List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }


    @Override
    public String toString() {
        return super.toString() +"UserVo{" +
                "orderList=" + orderList +
                '}';
    }
}
