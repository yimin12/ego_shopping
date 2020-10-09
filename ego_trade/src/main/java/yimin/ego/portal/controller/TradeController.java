package yimin.ego.portal.controller;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/7 12:55
 *   @Description :
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import yimin.ego.commons.pojo.OrderPojo;
import yimin.ego.service.TradeService;

import java.util.Map;

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;
    @RequestMapping("/order/create.html")
    public String createOrder(OrderPojo orderPojo, Model model){
        Map<String, Object> result = tradeService.createOrder(orderPojo);
        if(result != null){
            model.addAllAttributes(result);
            return "success";
        }
        model.addAttribute("message", "create trade failed");
        return "error/exception";
    }
}
