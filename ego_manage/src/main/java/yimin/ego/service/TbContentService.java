package yimin.ego.service;

import yimin.ego.commons.pojo.EasyUIDatagrid;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.pojo.TbContent;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 13:38
 *   @Description :
 *
 */
public interface TbContentService {

    /**
     * Paging Query
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDatagrid showContent(Long categoryId, int page, int rows);

    /**
     * Insert
     * @param tbContent
     * @return
     */
    EgoResult insert(TbContent tbContent);

    /**
     * Update
     * @param tbContent
     * @return
     */
    EgoResult update(TbContent tbContent);

    /**
     * Delete
     * @param ids
     * @return
     */
    EgoResult delete(long[] ids);
}
