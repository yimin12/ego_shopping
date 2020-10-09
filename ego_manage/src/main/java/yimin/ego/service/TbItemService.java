package yimin.ego.service;

import yimin.ego.commons.pojo.EasyUIDatagrid;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.pojo.TbItem;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 10:41
 *   @Description :
 *
 */
public interface TbItemService {

    /**
     *  this method return what the page need
     *  parameters is what page parsing
     */

    /**
     * @param page
     * @param rows
     * @return easyui
     */
    EasyUIDatagrid showItem(int page, int rows);

    /**
     * Update status
     * @param ids
     * @param status
     * @return
     */
    EgoResult updateStatus(long[] ids, int status);

    /**
     * Insert item with its description
     * @param item
     * @param desc
     * @return
     */
    EgoResult insert(TbItem item, String desc);

    /**
     * Update item's description
     * @param item
     * @param desc
     * @return
     */
    EgoResult update(TbItem item, String desc);

    /**
     * Insert item's description and item's parameters
     * @param item
     * @param desc
     * @param itemParams
     * @return
     */
    EgoResult insert(TbItem item,String desc,String itemParams);

    /**
     * Update item's description and item's parameters
     * @param item
     * @param desc
     * @param itemParams
     * @param itemParamId
     * @return
     */
    EgoResult update(TbItem item,String desc,String itemParams,long itemParamId);
}
