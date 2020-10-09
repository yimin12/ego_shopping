package yimin.ego.search.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yimin.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/5 19:37
 *   @Description :
 *
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * Search query from solr and display the result
     * @param query
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("search.html")
    public String showSearch(String query, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "12") int size){
        model.addAllAttributes(searchService.search(query, page, size));
        return "search";
    }

    /**
     * Insert
     * @param ids
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public int insert(long[] ids){
        return searchService.insert(ids);
    }

    /**
     * Update
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public int delete(String [] id){
        return searchService.delete(id);
    }
}
