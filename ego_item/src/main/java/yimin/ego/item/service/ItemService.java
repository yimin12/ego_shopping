package yimin.ego.item.service;

import yimin.ego.commons.pojo.TbItemDetails;
import yimin.ego.item.pojo.ItemCategoryNav;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 0:57
 *   @Description :
 *
 */
public interface ItemService {


    /**
     * Display the navigation menu
     * @return
     */
    ItemCategoryNav showItemCat();

    /**
     * Display the item's details
     * @param id
     * @return
     */
    TbItemDetails showItem(Long id);

    /**
     * Display the item's description
     * @param itemId
     * @return
     */
    String showItemDesc(Long itemId);

    /**
     * Display the item's basic parameters
     * @param itemId
     * @return
     */
    String showItemParam(Long itemId);

}
