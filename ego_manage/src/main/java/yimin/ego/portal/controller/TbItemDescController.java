package yimin.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.service.TbItemDescService;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 19:20
 *   @Description :
 *
 */
@Controller
public class TbItemDescController {

    @Autowired
    private TbItemDescService tbItemDescService;

    /**
     * display the item's parameters
     */
    @RequestMapping("/rest/item/query/item/desc/{id}")
    @ResponseBody
    public EgoResult showDesc(@PathVariable Long id){
        return tbItemDescService.selectById(id);
    }
}
