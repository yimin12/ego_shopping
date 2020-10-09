package yimin.ego.service;

import yimin.ego.commons.pojo.EasyUITree;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.pojo.TbContentCategory;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 12:25
 *   @Description :
 *
 */
public interface TbContentCategoryService {

    /**
     * display the tree type content
     * @param pid
     * @return easyui tree's data
     */
    List<EasyUITree> showContentCategory(Long pid);

    /**
     * Insert
     * @param tbContentCategory
     * @return
     */
    EgoResult insert(TbContentCategory tbContentCategory);

    /**
     * Update
     * @param tbContentCategory
     * @return
     */
    EgoResult update(TbContentCategory tbContentCategory);

    /**
     * Delete
     * @param id
     * @return
     */
    EgoResult delete(Long id);
}
