package yimin.ego.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yimin.ego.item.pojo.ItemCategoryNav;
import yimin.ego.item.service.ItemService;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 1:06
 *   @Description :
 *
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    @CrossOrigin
    public ItemCategoryNav showItemCat(){
        return itemService.showItemCat();
    }

    /**
     * display the details of the item
     * @param id
     * @return
     */
    @RequestMapping("/item/{id}.html")
    public String showItem(@PathVariable Long id, Model model){
        model.addAttribute("item", itemService.showItem(id));
        return "item";
    }

    @RequestMapping("/item/desc/{id}.html")
    @ResponseBody
    public String showItemDesc(@PathVariable Long id){
        return itemService.showItemDesc(id);
    }

    @RequestMapping("/item/param/{id}.html")
    @ResponseBody
    public String showParamItem(@PathVariable Long id){
        return itemService.showItemDesc(id);
    }


}

