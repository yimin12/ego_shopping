package yimin.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yimin.ego.portal.service.PortalService;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 0:45
 *   @Description :
 *
 */
@Controller
public class PortalController {

    @Autowired
    private PortalService portalService;

    @RequestMapping("/")
    public String welcome(Model model){
        model.addAttribute("ad1",portalService.showBigAd());
        return "index";
    }

    @RequestMapping("/bigad")
    @ResponseBody
    public String bigad(){
        System.out.println("executed bigad2");
        portalService.showBigAd2();
        return "ok";
    }
}
