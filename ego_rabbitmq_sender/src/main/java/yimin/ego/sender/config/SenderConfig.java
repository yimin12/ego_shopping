package yimin.ego.sender.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 23:12
 *   @Description :
 *
 */
@Configuration
public class SenderConfig {

    @Value("${ego.rabbitmq.content.queueName}")
    private String contentQueue;

    @Value("${ego.rabbitmq.item.insertName}")
    private String itemInsert;

    @Value("${ego.rabbitmq.item.deleteName}")
    private String itemDelete;

    @Value("${ego.rabbitmq.order.createorder}")
    private String createOrder;

    @Value("${ego.rabbitmq.order.deletecart}")
    private String deleteCart;

    @Value("${ego.rabbitmq.mail}")
    private String mail;

    /**
     * If no queue, it will build queue
     */
    @Bean
    public Queue queue(){
        return new Queue(contentQueue);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("amq.direct");
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).withQueueName();
    }

    /**
     * 写下面两个方法的目的：第一次发送消息时，
     * 如果没有队列创建队列，并绑定到amq.direct交换器上
     */
    @Bean
    public Queue queueItemInsert(){
        return new Queue(itemInsert);
    }
    @Bean
    public Binding binding2(Queue queueItemInsert, DirectExchange directExchange){
        return BindingBuilder.bind(queueItemInsert).to(directExchange).withQueueName();
    }

    @Bean
    public Queue queueItemDelete() {
        return new Queue(itemDelete);
    }
    @Bean
    public Binding binding3(Queue queueItemDelete, DirectExchange directExchange) {
        return BindingBuilder.bind(queueItemDelete).to(directExchange).withQueueName();
    }

    /**
     * Order queue
     * @return
     */
    @Bean
    public Queue queueCreateOrder() {
        return new Queue(createOrder);
    }

    @Bean
    public Binding binding4(Queue queueCreateOrder, DirectExchange directExchange) {
        return BindingBuilder.bind(queueCreateOrder).to(directExchange).withQueueName();
    }

    /**
     * delete the item of cart
     * @return
     */
    @Bean
    public Queue queueDeleteCart() {
        return new Queue(deleteCart);
    }

    @Bean
    public Binding binding5(Queue queueDeleteCart, DirectExchange directExchange) {
        return BindingBuilder.bind(queueDeleteCart).to(directExchange).withQueueName();
    }

    @Bean
    public Queue queueMail() {
        return new Queue(mail);
    }

    @Bean
    public Binding binding6(Queue queueMail, DirectExchange directExchange) {
        return BindingBuilder.bind(queueMail).to(directExchange).withQueueName();
    }
}
