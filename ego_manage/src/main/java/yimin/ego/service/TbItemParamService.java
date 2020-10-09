package yimin.ego.service;

import yimin.ego.commons.pojo.EasyUIDatagrid;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.pojo.TbItemParam;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 22:44
 *   @Description :
 *
 */
public interface TbItemParamService {

    EasyUIDatagrid showItemParam(int page, int rows);

    /**
     * Search item parameters by given category id
     * @param catId
     * @return
     */
    EgoResult showItemParamByCatid(Long catId);

    /**
     * Insert
     * @param tbItemParam
     * @return
     */
    EgoResult insert(TbItemParam tbItemParam);

    /**
     * Delete
     * @param ids
     * @return
     */
    EgoResult delete(long [] ids);
}
