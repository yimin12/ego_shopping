package yimin.ego.portal.controller;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/9/30 21:25
 *   @Description :
 *      Main logic for the view layer: it is controller
 */


import org.springframework.web.bind.annotation.PathVariable;
import yimin.ego.commons.pojo.EgoResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {

    /** login page
     */
    @RequestMapping("/")
    public String login() {
        return "login";
    }

    /**
     * index.jsp
     */
    @RequestMapping("/main")
    public String showIndex() {
        return "index";
    }

    /**
     * return to controller if login successfully
     */
    @RequestMapping("/loginSuccess")
    @ResponseBody
    public EgoResult loginSuccess() {
        return EgoResult.ok();
    }


    /**
     * implement the restful type
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

    @RequestMapping("/rest/page/item-edit")
    public String showEdit(){
        return "item-edit";
    }
}
