package yimin.ego.cart.pojo;

import yimin.ego.commons.pojo.CartPojo;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/7 12:04
 *   @Description :
 *
 */
public class OrderCartPojo extends CartPojo {

    private Boolean enough;

    public Boolean getEnough(){
        return enough;
    }

    public void setEnough(Boolean enough) {
        this.enough = enough;
    }
}
