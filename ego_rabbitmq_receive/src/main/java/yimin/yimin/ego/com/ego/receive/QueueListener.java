package yimin.yimin.ego.com.ego.receive;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import yimin.ego.commons.pojo.*;
import yimin.ego.commons.utils.HttpClientUtil;
import yimin.ego.commons.utils.IDUtils;
import yimin.ego.commons.utils.JsonUtils;
import yimin.ego.dubbo.service.TbContentDubboService;
import yimin.ego.dubbo.service.TbItemDubboService;
import yimin.ego.dubbo.service.TbOrderDubboService;
import yimin.ego.mail.MyMailSender;
import yimin.ego.pojo.*;

import java.io.*;
import java.util.*;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 23:57
 *   @Description :
 *
 */
@Component
public class QueueListener {

    @Value("${ego.bigad.categoryId}")
    private Long bigadId;
    @Value("${ego.search.insert}")
    private String searchInsertUrl;
    @Value("${ego.search.delete}")
    private String searchDeleteUrl;
    @Value("${ego.cart.deletecarturl}")
    private String cartDeleteUrl;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbContentDubboService tbContentDubboService;
    @Reference
    private TbOrderDubboService tbOrderDubboService;
    @Autowired
    private MyMailSender myMailSender;

    /**
     * simplest way to implement. When there is no message leaving in the queue, we will get error if we try to init
     * @RabbitListener(queues = "content") guarantee that we can init successfully even though there is no message in the queue
     */
    @RabbitListener(bindings = {@QueueBinding(value=@Queue(name="${ego.rabbitmq.content.queueName}"),exchange = @Exchange(name="amq.direct"))})
    public void content(Object object){

        /**
         * sync the redis
         * step 1: grab the ad info from mysql, parse it to the consumer of dubbo
         * step 2: put the information to the redis
         */
        List<TbContent> list = tbContentDubboService.selectAllByCategoryId(bigadId);
        List<BigAd> listBigAd = new ArrayList<>();
        for(TbContent tbContent:list){
            BigAd bigAd = new BigAd();
            bigAd.setAlt("");
            bigAd.setHeight(240);
            bigAd.setHeightB(240);
            bigAd.setHref(tbContent.getUrl());
            bigAd.setSrc(tbContent.getPic());
            bigAd.setSrcB(tbContent.getPic2());
            bigAd.setWidth(670);
            bigAd.setWidthB(550);
            listBigAd.add(bigAd);
        }
        redisTemplate.opsForValue().set("yimin.ego.portal::bigad", JsonUtils.objectToJson(listBigAd));

        // or we can directly call the function of ego_port
        // HttpClientUtil.doGet("http://localhost:8082/bigad");
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = "${ego.rabbitmq.item.insertName}"), exchange = @Exchange(name = "amq.direct"))})
    public void itemInsert(String id){

        /**
         * step 1: sync the solr
         */
        System.out.println("get the id: " + id);
        Map<String, String> param = new HashMap<>();
        param.put("id", id+"");
        String result = HttpClientUtil.doPost(searchInsertUrl, param);
        System.out.println("The return type is " + result);

        /**
         * step 2: sync the redis
         */

        String[] ids = id.split(",");
        for(String idArr: ids) {
            String key = "com.ego.item::details:"+idArr;
            TbItem tbItem =
                    tbItemDubboService.selectById(Long.parseLong(idArr));
            TbItemDetails details = new TbItemDetails();
            details.setId(tbItem.getId());
            details.setPrice(tbItem.getPrice());
            details.setSellPoint(tbItem.getSellPoint());
            details.setTitle(tbItem.getTitle());
            String img = tbItem.getImage();
            details.setImages(img!=null&&!img.equals("")?img.split(","):new String[1]);
            redisTemplate.opsForValue().set(key,details);
        }
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = "${ego.rabbitmq.item.deleteName}"), exchange = @Exchange(name = "amq.direct"))})
    public void itemDelete(String id) {
        System.out.println("delete id : " + id);

        /**
         * step 1: sync the solr
         */
        Map<String, String> param = new HashMap<>();
        param.put("id", id + "");
        String result = HttpClientUtil.doPost(searchDeleteUrl, param);
        System.out.println("return id ："+result);

        /**
         * step 2: sync the redis
         */
        String[] ids = id.split(",");
        for(String idArr : ids){
            String key = "yimin.ego.item::details" + idArr;
            redisTemplate.delete(key);
        }
    }

    /**
     * create order queue
     * @param msg
     * @return
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = "${ego.rabbitmq.order.createorder}"), exchange = @Exchange(name = "amq.direct"))})
    public String orderCreate(Message msg){
        try{
            // deserialization
            byte[] body = msg.getBody();
            InputStream is = new ByteArrayInputStream(body);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            OrderPojo orderPojo = (OrderPojo) objectInputStream.readObject();
            List<TbOrderItem> listItem = orderPojo.getOrderItems();
            for(TbOrderItem tbOrderItem : listItem){
                TbItem tbItem = tbItemDubboService.selectById(Long.parseLong(tbOrderItem.getItemId()));
                if(tbItem.getNum() < tbOrderItem.getNum()){
                    return null;
                }
            }
            // the remaining of item is sufficient
            TbOrder tbOrder = new TbOrder();
            tbOrder.setPayment(orderPojo.getPayment());
            tbOrder.setPaymentType(orderPojo.getPaymentType());
            // order id
            String id = "" + IDUtils.genItemId();
            tbOrder.setOrderId(id);
            Date date = new Date();
            tbOrder.setCreateTime(date);
            tbOrder.setUpdateTime(date);
            // tbOrderitem
            for(TbOrderItem tbOrderItem : listItem){
                tbOrderItem.setId(IDUtils.genItemId() + "");
                tbOrderItem.setOrderId(id);
            }
            // tbOrdershipping
            TbOrderShipping orderShipping = orderPojo.getTbOrderShipping();
            orderShipping.setOrderId(id);
            orderShipping.setCreated(date);
            orderShipping.setUpdated(date);

            // insert data
            int index = tbOrderDubboService.insert(tbOrder, listItem, orderShipping);
            if(index == 1){
                return id;
            }
            System.out.println(orderPojo);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = "${ego.rabbitmq.order.deletecart}"), exchange = @Exchange(name = "amq.direct"))})
    public void deleteCart(Message msg){
        try{
            byte[] body = msg.getBody();
            InputStream is = new ByteArrayInputStream(body);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            DeleteCartPojo deleteCartPojo = (DeleteCartPojo) objectInputStream.readObject();
            Map<String, String> param = new HashMap<>();
            param.put("userId", deleteCartPojo.getUserId() + "");
            param.put("itemId", deleteCartPojo.getItemIds());
            HttpClientUtil.doPost(cartDeleteUrl, param);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue(name = "${ego.rabbitmq.mail}"), exchange = @Exchange(name = "amq.direct"))})
    public void mail(Message msg){
        try{
            byte[] body = msg.getBody();
            InputStream is = new ByteArrayInputStream(body);
            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            RabbitmqMailPojo rabbitmqMailPojo = (RabbitmqMailPojo) objectInputStream.readObject();
            myMailSender.send(rabbitmqMailPojo.getEmail(), rabbitmqMailPojo.getOrderId());
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
