package yimin.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.service.TbItemParamItemService;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 13:16
 *   @Description :
 *
 */
@RestController
public class TbItemParamItemController {

    @Autowired
    private TbItemParamItemService tbItemParamItemService;

    /**
     * query the information of Item based on given id
     * @param itemId
     * @return
     */
    @RequestMapping("/rest/item/param/item/query/{itemId}")
    public EgoResult showItemparamItem(@PathVariable Long itemId){
        return tbItemParamItemService.showItemParamItem(itemId);
    }

}
