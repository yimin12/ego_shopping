package yimin.ego.commons.pojo;

import yimin.ego.pojo.TbOrderItem;
import yimin.ego.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 22:26
 *   @Description :
 *
 */
public class OrderPojo implements Serializable {

    public static final long serialVersionUID = 1L;
    private String payment;
    private Integer paymentType;
    private TbOrderShipping tbOrderShipping;

    private List<TbOrderItem> orderItems;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public TbOrderShipping getTbOrderShipping() {
        return tbOrderShipping;
    }

    public void setTbOrderShipping(TbOrderShipping tbOrderShipping) {
        this.tbOrderShipping = tbOrderShipping;
    }

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
