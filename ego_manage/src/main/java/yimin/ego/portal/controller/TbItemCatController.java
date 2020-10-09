package yimin.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yimin.ego.commons.pojo.EasyUITree;
import yimin.ego.service.TbItemCatService;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 17:31
 *   @Description :
 *
 */
@Controller
public class TbItemCatController {

    @Autowired
    private TbItemCatService tbItemCatService;

    /**
     *
     * @param id id 是easyui 底层的参数名，固定叫做id。表示父id
     * @return
     */
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITree> showTree(@RequestParam(defaultValue = "0") Long id){
        return tbItemCatService.showTree(id);
    }
}
