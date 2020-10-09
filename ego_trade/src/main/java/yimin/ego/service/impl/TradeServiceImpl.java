package yimin.ego.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yimin.ego.commons.pojo.DeleteCartPojo;
import yimin.ego.commons.pojo.OrderPojo;
import yimin.ego.commons.pojo.RabbitmqMailPojo;
import yimin.ego.commons.utils.ServletUtil;
import yimin.ego.pojo.TbOrderItem;
import yimin.ego.pojo.TbUser;
import yimin.ego.sender.Send;
import yimin.ego.service.TradeService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/7 14:14
 *   @Description :
 *
 */
@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private Send send;
    @Value("${ego.rabbitmq.order.createorder}")
    private String createOrder;
    @Value("${ego.rabbitmq.order.deletecart}")
    private String deleteCart;
    @Value("${ego.rabbitmq.mail}")
    private String mail;

    @Override
    public Map<String, Object> createOrder(OrderPojo orderPojo) {

        String result = (String) send.sendAndReceive(createOrder, orderPojo);
        System.out.println("result : " + result);
        // delete the item in cart
        DeleteCartPojo dcp = new DeleteCartPojo();
        TbUser tbUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        dcp.setUserId(tbUser.getId());
        List<TbOrderItem> list = orderPojo.getOrderItems();
        StringBuffer sf = new StringBuffer();
        for(int i = 0; i < list.size(); i++){
            sf.append(list.get(i).getItemId());
            if(i < list.size() - 1){
                sf.append(",");
            }
        }
        dcp.setItemIds(sf.toString());
        send.send(deleteCart, dcp);

        RabbitmqMailPojo rmp = new RabbitmqMailPojo();
        rmp.setOrderId(result);
        rmp.setEmail(tbUser.getEmail());
        send.send(mail, rmp);

        Map<String, Object> resultMap = new HashMap<>();
        if(result != null){
            resultMap.put("orderId", result);
            resultMap.put("payment", orderPojo.getPayment());
            // 当天11点之前下订单，预计当天下午送到
            // 11点到23点之前下单，预计第二天上午送到
            // 23点之后第二天下午送到。
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(hour < 11){
                calendar.set(Calendar.HOUR_OF_DAY, 17);
            } else if(hour > 11 && hour < 23){
                calendar.add(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 9);
            } else {
                calendar.add(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 17);
            }
            resultMap.put("date", calendar.getTime());
            return resultMap;
        }
        return null;
    }
}
