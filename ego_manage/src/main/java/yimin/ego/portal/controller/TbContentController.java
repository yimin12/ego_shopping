package yimin.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yimin.ego.commons.pojo.EasyUIDatagrid;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.pojo.TbContent;
import yimin.ego.service.TbContentService;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 13:43
 *   @Description :
 *      @RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
        1) 如果只是使用@RestController注解Controller，则Controller中的方法无法返回jsp页面，或者html，配置的视图解析器 InternalResourceViewResolver不起作用，返回的内容就是Return 里的内容。
        2) 如果需要返回到指定页面，则需要用 @Controller配合视图解析器InternalResourceViewResolver才行。
           如果需要返回JSON，XML或自定义mediaType内容到页面，则需要在对应的方法上加上@ResponseBody注解。
        1.使用@Controller 注解，在对应的方法上，视图解析器可以解析return 的jsp,html页面，并且跳转到相应页面
          若返回json等内容到页面，则需要加@ResponseBody注解
 */
@RestController
public class TbContentController {

    @Autowired
    private TbContentService tbContentService;

    /**
     * display
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/content/query/list")
    public EasyUIDatagrid showContent(Long categoryId, int page, int rows){
        return tbContentService.showContent(categoryId,page,rows);
    }

    /**
     * insert
     * @param tbContent
     * @return
     */
    @RequestMapping("/content/save")
    public EgoResult insert(TbContent tbContent){
        return tbContentService.insert(tbContent);
    }

    /**
     * update
     * @param tbContent
     * @return
     */
    @RequestMapping("/rest/content/edit")
    public EgoResult update(TbContent tbContent){
        return tbContentService.update(tbContent);
    }

    /**
     * delete
     * @param ids
     * @return
     */
    @RequestMapping("/content/delete")
    public EgoResult delete(long[] ids){
        return tbContentService.delete(ids);
    }
}
