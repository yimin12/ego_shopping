package yimin.ego.portal.controller;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 0:05
 *   @Description :
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yimin.ego.commons.pojo.EasyUIDatagrid;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.pojo.TbItemParam;
import yimin.ego.service.TbItemParamService;

@RestController
public class TbItemParamController {

    @Autowired
    private TbItemParamService tbItemParamService;

    /**
     * @RestController = @Controller + add each method by @ResponseBody
     */
    @RequestMapping("/item/param/list")
    public EasyUIDatagrid showItemparam(int page, int rows){
        return tbItemParamService.showItemParam(page, rows);
    }

    /**
     * 根据类目id 查询规格模板
     * @param id
     * @return
     */
    @RequestMapping("/item/param/query/itemcatid/{id}")
    public EgoResult showItemParamByCatId(@PathVariable Long id){
        return tbItemParamService.showItemParamByCatid(id);
    }

    @RequestMapping("/item/param/save/{catId}")
    public EgoResult insert(TbItemParam tbItemParam, @PathVariable Long catId){
        tbItemParam.setItemCatId(catId);
        return tbItemParamService.insert(tbItemParam);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping("/item/param/delete")
    public EgoResult delete(long[] ids){
        return tbItemParamService.delete(ids);
    }
}
