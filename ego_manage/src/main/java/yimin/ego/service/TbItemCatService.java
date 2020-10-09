package yimin.ego.service;

import yimin.ego.commons.pojo.EasyUITree;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 17:27
 *   @Description :
 *
 */
public interface TbItemCatService {

    /**
     * Show all tree type menu
     * @param pid
     * @return
     */
    List<EasyUITree> showTree(Long pid);

}
