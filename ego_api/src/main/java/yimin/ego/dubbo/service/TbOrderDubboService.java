package yimin.ego.dubbo.service;

import yimin.ego.commons.exception.DaoException;
import yimin.ego.pojo.TbOrder;
import yimin.ego.pojo.TbOrderItem;
import yimin.ego.pojo.TbOrderShipping;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/7 13:01
 *   @Description :
 *
 */
public interface TbOrderDubboService {

    /**
     * Create an new order
     * @param tbOrder
     * @param list
     * @param tbOrderShipping
     * @return
     * @throws DaoException
     */
    int insert(TbOrder tbOrder, List<TbOrderItem> list, TbOrderShipping tbOrderShipping) throws DaoException;
}
/**
 *CREATE TABLE `tb_order` (
 *   `order_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '订单id',
 *   `payment` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分',
 *   `payment_type` int DEFAULT NULL COMMENT '支付类型，1、在线支付，2、货到付款',
 *   `post_fee` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分',
 *   `status` int DEFAULT NULL COMMENT '状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
 *   `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
 *   `update_time` datetime DEFAULT NULL COMMENT '订单更新时间',
 *   `payment_time` datetime DEFAULT NULL COMMENT '付款时间',
 *   `consign_time` datetime DEFAULT NULL COMMENT '发货时间',
 *   `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
 *   `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
 *   `shipping_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物流名称',
 *   `shipping_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物流单号',
 *   `user_id` bigint DEFAULT NULL COMMENT '用户id',
 *   `buyer_message` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '买家留言',
 *   `buyer_nick` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '买家昵称',
 *   `buyer_rate` int DEFAULT NULL COMMENT '买家是否已经评价',
 *   PRIMARY KEY (`order_id`),
 *   KEY `create_time` (`create_time`),
 *   KEY `buyer_nick` (`buyer_nick`),
 *   KEY `status` (`status`),
 *   KEY `payment_type` (`payment_type`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 */