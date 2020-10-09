package yimin.ego.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 23:10
 *   @Description :
 *
 */
@Component
public class Send {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String queue, Object obj){
        amqpTemplate.convertAndSend("amq.direct", queue, obj);
    }

    /**
     * return the value from receiver
     * @param queue
     * @param object
     * @return
     */
    public Object sendAndReceive(String queue, Object object){
        return amqpTemplate.convertSendAndReceive("amq.direct", queue, object);
    }
}
