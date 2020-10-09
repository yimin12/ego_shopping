package yimin.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yimin.ego.commons.pojo.EasyUIDatagrid;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.pojo.TbItem;
import yimin.ego.service.TbItemService;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 10:48
 *   @Description :
 *
 */
@Controller
public class TbItemController {

    @Autowired
    private TbItemService tbItemService;

    /**
     * show the item of each page
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDatagrid showItem(int page, int rows){
        return tbItemService.showItem(page, rows);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public EgoResult delete(long[] ids){
        return tbItemService.updateStatus(ids,3);
    }
    /**
     * 上架
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public EgoResult reshelf(long[] ids){
        return tbItemService.updateStatus(ids,1);
    }
    /**
     * 下架
     * @param ids
     * @return
     */
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public EgoResult instock(long[] ids){
        return tbItemService.updateStatus(ids,2);
    }

    /**
     * update the items
     * @param item
     * @param desc
     * @return
     */

    @RequestMapping("/item/save")
    @ResponseBody
    public EgoResult insert(TbItem item,String desc,String itemParams){
        return tbItemService.insert(item,desc,itemParams);
    }

    /**
     *  update the info in front end
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping("/rest/item/update")
    @ResponseBody
    public EgoResult update(TbItem item,String desc,String itemParams,long itemParamId){
        return tbItemService.update(item,desc,itemParams,itemParamId);
    }

}
