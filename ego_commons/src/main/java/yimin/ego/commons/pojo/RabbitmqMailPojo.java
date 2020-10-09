package yimin.ego.commons.pojo;

import java.io.Serializable;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/7 0:34
 *   @Description :
 *
 */
public class RabbitmqMailPojo implements Serializable {

    public static final long serialVersionUID = 1L;
    private String email;
    private String orderId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
