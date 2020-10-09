package yimin.ego.dubbo.service;

import yimin.ego.pojo.TbItemParamItem;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 13:03
 *   @Description :
 *
 */
public interface TbItemParamItemDubboService {

    /**
     * search the item params relationship by given item id
     * @param itemId
     * @return
     */
    TbItemParamItem selectByItemId(Long itemId);
}

/**
 *CREATE TABLE `tb_item_param_item` (
 *   `id` bigint NOT NULL AUTO_INCREMENT,
 *   `item_id` bigint DEFAULT NULL COMMENT '商品ID',
 *   `param_data` text COMMENT '参数数据，格式为json格式',
 *   `created` datetime DEFAULT NULL,
 *   `updated` datetime DEFAULT NULL,
 *   PRIMARY KEY (`id`),
 *   KEY `item_id` (`item_id`) USING BTREE
 * ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='商品规格和商品的关系表';
 */