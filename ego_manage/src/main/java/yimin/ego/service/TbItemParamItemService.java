package yimin.ego.service;

import yimin.ego.commons.pojo.EgoResult;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 12:58
 *   @Description :
 *
 */
public interface TbItemParamItemService {

    /**
     * show the info of item based on given item id
     * @param itemId
     * @return
     */
    EgoResult showItemParamItem(Long itemId);
}
