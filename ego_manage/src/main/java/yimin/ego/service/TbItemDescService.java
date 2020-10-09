package yimin.ego.service;

import yimin.ego.commons.pojo.EgoResult;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 19:17
 *   @Description :
 *
 */
public interface TbItemDescService {

    /**
     * Search item's description
     * @param id
     * @return
     */
    EgoResult selectById(Long id);

}
