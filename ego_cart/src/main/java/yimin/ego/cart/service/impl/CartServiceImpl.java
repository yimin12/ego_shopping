package yimin.ego.cart.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import yimin.ego.cart.pojo.OrderCartPojo;
import yimin.ego.cart.service.CartService;
import yimin.ego.commons.pojo.CartPojo;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.commons.pojo.TbItemDetails;
import yimin.ego.commons.utils.CookieUtils;
import yimin.ego.commons.utils.JsonUtils;
import yimin.ego.commons.utils.ServletUtil;
import yimin.ego.dubbo.service.TbItemDubboService;
import yimin.ego.pojo.TbItem;
import yimin.ego.pojo.TbUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 21:55
 *   @Description :
 *
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${ego.item.details.rediskey}")
    private String detailsKey;
    @Value("${ego.cart.tempcart}")
    private String tempCartKey;
    // cart:
    @Value("${ego.cart.rediskey}")
    private String cartRedisKey;
    @Reference
    private TbItemDubboService tbItemDubboService;

    /**
     * There are two cases possible:
     *      1. Add items after user login (cart is personal)
     *      2. Add items before user login (cart is public and temporary)
     * The key stored in redis is concatenated by cartRedisKey and user's id
     *
     * @param id
     * @param num
     */
    @Override
    public void addCart(Long id, int num) {
        // Check tbUser from spring session's attribute "loginUser", if user has login, we will put key "loginUser" to the session
        TbUser tbUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        // case 1: If user login
        if(tbUser != null){
            List<CartPojo> list = new ArrayList<>();
            String key = cartRedisKey + tbUser.getId();
            // case 1.1: If cart already exist this kind of item
            if(redisTemplate.hasKey(key)){
                // Query from redis, get key;
                list = (List<CartPojo>) redisTemplate.opsForValue().get(key);
                for(CartPojo cp : list){
                    if(cp.getId().equals(id)){
                        cp.setNum(cp.getNum()+num);
                        // update the cart info to redis
                        redisTemplate.opsForValue().set(key, list);
                        return;
                    }
                }
            }
            // case 1.2: if cart do not contain item, create new one
            String detailsRedisKey = detailsKey + id;
            TbItemDetails tbItemDetails = (TbItemDetails) redisTemplate.opsForValue().get(detailsRedisKey);
            CartPojo cartPojo = new CartPojo();
            cartPojo.setId(tbItemDetails.getId());
            cartPojo.setImages(tbItemDetails.getImages());
            cartPojo.setNum(num);
            cartPojo.setPrice(tbItemDetails.getPrice());
            cartPojo.setTitle(tbItemDetails.getTitle());
            list.add(cartPojo);
            redisTemplate.opsForValue().set(key, list);
            return;
        }
        // Case 2: If there is no user login
        Map<Long, CartPojo> tempCart = new HashMap<>();
        // Get cookie with key of tempCartKey, empty if it is the first time operation
        String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCartKey);
        if(Strings.isNotEmpty(cookieValue)){
            // current cookie mapping to temporary cart
            // determine whether exist current items
            tempCart = JsonUtils.jsonToMap(cookieValue, Long.class, CartPojo.class);
            if(tempCart.containsKey(id)){
                CartPojo cartPojo = tempCart.get(id);
                cartPojo.setNum(cartPojo.getNum() + num);
                // Update the cookie info
                CookieUtils.doSetCookieBase64(ServletUtil.getRequest(), ServletUtil.getResponse(), tempCartKey,JsonUtils.objectToJson(tempCart),2592000);
                return;
            }
        }
        // if the cookie is not null, update the cookie with cookieUtils
        String key = detailsKey+id;
        TbItemDetails tbItemDetails = (TbItemDetails) redisTemplate.opsForValue().get(key);
        CartPojo cartPojo = new CartPojo();
        cartPojo.setId(tbItemDetails.getId());
        cartPojo.setImages(tbItemDetails.getImages());
        cartPojo.setNum(num);
        cartPojo.setPrice(tbItemDetails.getPrice());
        cartPojo.setTitle(tbItemDetails.getTitle());
        // add it to map
        tempCart.put(id,cartPojo);
        // add to temporary cart
        CookieUtils.doSetCookieBase64(ServletUtil.getRequest(),ServletUtil.getResponse(),tempCartKey, JsonUtils.objectToJson(tempCart),2592000);
    }

    /**
     * Fetch the cart info from redis if user has login
     * Fetch the cart info from cookie if no user login (cookie contains cartpojo map)
     * @return
     */
    @Override
    public List<CartPojo> showCart() {
        TbUser tbUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        // user's cart
        if(tbUser != null){
            String key = cartRedisKey + tbUser.getId();
            List<CartPojo> list = (List<CartPojo>) redisTemplate.opsForValue().get(key);
            return list;
        }

        // temporary cart(no user login)
        List<CartPojo> list = new ArrayList<>();
        String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCartKey);
        if(Strings.isNotEmpty(cookieValue)){
            Map<Long, CartPojo> map = JsonUtils.jsonToMap(cookieValue, Long.class, CartPojo.class);
            for(Long id : map.keySet()){
                list.add(map.get(id));
            }
        }
        return list;
    }

    @Override
    public EgoResult updateNum(Long id, int num) {
        TbUser tbUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        // user's cart
        if(tbUser!=null){
            // update to redis
            String key = cartRedisKey + tbUser.getId();
            List<CartPojo> list = (List<CartPojo>) redisTemplate.opsForValue().get(key);
            for(CartPojo cp : list){
                // we should use equals when we encounter Long
                if(cp.getId().equals(id)){
                    cp.setNum(num);
                    break;
                }
            }
            redisTemplate.opsForValue().set(key,list);
        }else{
            // update to cookie
            String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCartKey);
            Map<Long,CartPojo> tempCart = JsonUtils.jsonToMap(cookieValue,Long.class,CartPojo.class);
            tempCart.get(id).setNum(num);
            CookieUtils.doSetCookieBase64(ServletUtil.getRequest(),ServletUtil.getResponse(),tempCartKey, JsonUtils.objectToJson(tempCart),2592000);
        }
        return EgoResult.ok();
    }

    @Override
    public EgoResult delete(Long id) {
        TbUser tbUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        // user's cart
        if(tbUser!=null){
            // delete operation in cart
            String key = cartRedisKey + tbUser.getId();
            List<CartPojo> list = (List<CartPojo>) redisTemplate.opsForValue().get(key);
            for(CartPojo cp : list){
                if(cp.getId().equals(id)){
                    list.remove(cp);
                    break;
                }
            }
            redisTemplate.opsForValue().set(key,list);
        }else {
            // delete in cookie
            String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCartKey);
            Map<Long, CartPojo> tempCart = JsonUtils.jsonToMap(cookieValue, Long.class, CartPojo.class);
            tempCart.remove(id);
            CookieUtils.doSetCookieBase64(ServletUtil.getRequest(), ServletUtil.getResponse(), tempCartKey, JsonUtils.objectToJson(tempCart), 2592000);
        }
        return EgoResult.ok();
    }

    /**
     * Display the checkout page
     * @param ids
     * @return
     */
    @Override
    public List<OrderCartPojo> showOrderCart(List<Long> ids) {
        List<OrderCartPojo> listResult = new ArrayList<>();
        TbUser tbUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        String key = cartRedisKey + tbUser.getId();
        // get the information of the cart
        List<CartPojo> list = (List<CartPojo>)redisTemplate.opsForValue().get(key);
        for(Long id : ids){
            for(CartPojo cp : list){
                if(cp.getId().equals(id)){
                    OrderCartPojo ocp = new OrderCartPojo();
                    // use BeanUtils to do deep copy
                    BeanUtils.copyProperties(cp, ocp);
                    // check the remaining products in database
                    TbItem tbItem = tbItemDubboService.selectById(id);
                    if(tbItem.getNum() >= cp.getNum()){
                        ocp.setEnough(true);
                    } else {
                        ocp.setEnough(false);
                    }
                    listResult.add(ocp);
                    break;
                }
            }
        }
        return listResult;
    }

    /**
     *
     * @param userId
     * @param ids
     * @return 1: delete successfully  0: delete failed
     */
    @Override
    public int deleteUsercartByItemId(Long userId, Long[] ids) {
        try{
            String key = cartRedisKey + userId;
            List<CartPojo> userCart = (List<CartPojo>) redisTemplate.opsForValue().get(key);
            for(Long id : ids){
                for(CartPojo cp : userCart){
                    if(cp.getId().equals(id)){
                        userCart.remove(cp);
                        break;
                    }
                }
            }
            redisTemplate.opsForValue().set(key, userCart);
            return 1;
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
