package yimin.ego.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yimin.ego.cart.service.CartService;
import yimin.ego.commons.pojo.EgoResult;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 21:24
 *   @Description :
 *
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * add to the cart
     * @param id
     * @param num
     * @return
     */
    @RequestMapping("/cart/add/{id}.html")
    public String addCart(@PathVariable Long id, int num){
        cartService.addCart(id, num);
        return "cartSuccess";
    }

    /**
     * display the info of cart
     * @param model
     * @return
     */
    @RequestMapping("/cart/cart.html")
    public String showCart(Model model){
        model.addAttribute("cartList", cartService.showCart());
        return "cart";
    }

    @RequestMapping(value = {"/cart/update/num/{id}/{num}.action","/service/cart/update/num/{id}/{num}"})
    @ResponseBody
    public EgoResult updateNum(@PathVariable Long id, @PathVariable int num){
        return cartService.updateNum(id, num);
    }

    /**
     * delete item from cart
     * @param id
     * @return
     */
    @RequestMapping("/cart/delete/{id}.action")
    public EgoResult delete(@PathVariable Long id){
        return cartService.delete(id);
    }

    /**
     * display the checking page
     * @return
     */
    @RequestMapping("/cart/order-cart.html")
    public String showOrderCart(@RequestParam("id") List<Long> id, Model model){
        model.addAttribute("cartList", cartService.showOrderCart(id));
        System.out.println(id);
        return "order-cart";
    }

    /**
     * delete all items by given ids
     * @param userId
     * @param ids
     * @return
     */
    @RequestMapping("/cart/deleteByIds")
    @ResponseBody
    public int deleteUserCartByItemIds(Long userId, Long[] ids){
        return cartService.deleteUsercartByItemId(userId, ids);
    }

}
