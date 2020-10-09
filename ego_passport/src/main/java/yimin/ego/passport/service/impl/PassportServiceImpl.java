package yimin.ego.passport.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import yimin.ego.commons.pojo.CartPojo;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.commons.utils.CookieUtils;
import yimin.ego.commons.utils.IDUtils;
import yimin.ego.commons.utils.JsonUtils;
import yimin.ego.commons.utils.ServletUtil;
import yimin.ego.dubbo.service.TbUserDubboService;
import yimin.ego.passport.service.PassportService;
import yimin.ego.pojo.TbUser;

import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 19:27
 *   @Description :
 *
 */
@Service
public class PassportServiceImpl implements PassportService {


    /**
     * we should merge the temporary cart with the user cart
     */
    @Reference
    private TbUserDubboService tbUserDubboService;
    // tempcart
    @Value("${ego.cart.tempcart}")
    private String tempCartCookeName;
    // cart:
    @Value("${ego.cart.rediskey}")
    private String cartRedisKey;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public EgoResult check(TbUser tbUser) {
        TbUser user = tbUserDubboService.selectByUser(tbUser);
        if(user != null){
            return EgoResult.ok();
        }
        return EgoResult.error("User already exist");
    }

    @Override
    public EgoResult register(TbUser tbUser) {
        tbUser.setId(IDUtils.genItemId());
        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);

        /*
        encrypt the password by using md5
         */
        String pwdEncrypt = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(pwdEncrypt);
        int index = tbUserDubboService.insert(tbUser);
        if(index == 1){
            return EgoResult.ok();
        }
        return EgoResult.error("register failed");
    }

    @Override
    public EgoResult login(TbUser tbUser) {
        String pwdMD5 = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(pwdMD5);
        TbUser user = tbUserDubboService.selectByUsernamePwd(tbUser);
        if(user != null){
            // put the user to Egoresult, controller will contain the information
            // merge the cart:
            //  1.delete cookies  2.put the info of temporary cart to user cart
            String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCartCookeName);
            if(Strings.isNotEmpty(cookieValue)){
                Map<Long, CartPojo> tempCart = JsonUtils.jsonToMap(cookieValue, Long.class, CartPojo.class);
                // put the data of temp cart to redis
                String key = cartRedisKey + user.getId();
                List<CartPojo> list = (List<CartPojo>) redisTemplate.opsForValue().get(key);
                for(Long id:tempCart.keySet()){
                    boolean exist = false;
                    for(CartPojo cp : list){
                        if(cp.getId().equals(id)){
                            cp.setNum(cp.getNum() + tempCart.get(id).getNum());
                            exist = true;
                            break;
                        }
                    }
                    if(!exist){
                        list.add(tempCart.get(id));
                    }
                }
                redisTemplate.opsForValue().set(key,list);
                // delete temporary cart
                CookieUtils.deleteCookie(ServletUtil.getRequest(), ServletUtil.getResponse(), tempCartCookeName);
            }
           return EgoResult.ok(user);
        }
        return EgoResult.error("invalid username or password");
    }
}
