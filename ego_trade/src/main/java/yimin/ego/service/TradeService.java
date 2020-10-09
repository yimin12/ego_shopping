package yimin.ego.service;

import yimin.ego.commons.pojo.OrderPojo;

import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/7 14:10
 *   @Description :
 *
 */
public interface TradeService {


    /**
     *
     * @param orderPojo
     * @return
     */
    Map<String, Object> createOrder(OrderPojo orderPojo);
}
