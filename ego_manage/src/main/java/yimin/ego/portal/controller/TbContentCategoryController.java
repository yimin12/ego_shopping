package yimin.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yimin.ego.commons.pojo.EasyUITree;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.pojo.TbContentCategory;
import yimin.ego.service.TbContentCategoryService;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 12:32
 *   @Description :
 *
 */
@RestController
public class TbContentCategoryController {

    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    /**
     * display the content
     * @param id
     * @return
     */
    @RequestMapping("/content/category/list")
    public List<EasyUITree> showContentCategory(@RequestParam(defaultValue = "0") Long id){
        return tbContentCategoryService.showContentCategory(id);
    }

    /**
     * insert a new node
     * @param category
     * @return
     */
    @RequestMapping("/content/category/create")
    public EgoResult insert(TbContentCategory category){
        return tbContentCategoryService.insert(category);
    }

    /**
     * rename the new node
     * @param tbContentCategory
     * @return
     */
    @RequestMapping("/content/category/update")
    public EgoResult update(TbContentCategory tbContentCategory){
        return tbContentCategoryService.update(tbContentCategory);
    }

    /**
     * delete the node by given id
     * @param id
     * @return
     */
    @RequestMapping("/content/category/delete")
    public EgoResult delete(Long id){
        return tbContentCategoryService.delete(id);
    }
}
