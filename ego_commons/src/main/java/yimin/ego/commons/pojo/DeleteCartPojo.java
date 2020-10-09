package yimin.ego.commons.pojo;

import java.io.Serializable;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 22:25
 *   @Description :
 *
 */
public class DeleteCartPojo implements Serializable {

    public static final long serialVersionID=1L;
    private Long userId;
    private String itemIds;

    public static long getSerialVersionID() {
        return serialVersionID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }
}
