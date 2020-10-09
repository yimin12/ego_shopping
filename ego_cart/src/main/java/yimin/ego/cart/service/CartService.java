package yimin.ego.cart.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import yimin.ego.cart.pojo.OrderCartPojo;
import yimin.ego.commons.pojo.CartPojo;
import yimin.ego.commons.pojo.EgoResult;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 21:54
 *   @Description :
 *
 */
public interface CartService {

    /**
     * Add items to cart
     * @param id
     * @param num
     */
    void addCart(Long id, int num);

    /**
     * Show items in cart
     * @return
     */
    List<CartPojo> showCart();

    /**
     * Update the number of item in cart
     * @param id
     * @param num
     * @return EgoResult
     */
    EgoResult updateNum(Long id, int num);

    /**
     * Delete item in cart by given id;
     * @param id
     * @return EgoResult
     */
    EgoResult delete(Long id);

    /**
     * Display the checkout page
     * @param ids
     * @return
     */
    List<OrderCartPojo> showOrderCart(List<Long> ids);

    /**
     * Delete items in user's cart by given items' id
     * @param userId
     * @param ids
     * @return
     */
    int deleteUsercartByItemId(Long userId, Long[] ids);

}
