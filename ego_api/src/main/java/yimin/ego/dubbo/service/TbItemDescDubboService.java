package yimin.ego.dubbo.service;

import yimin.ego.pojo.TbItemDesc;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 19:11
 *   @Description :
 *
 */
public interface TbItemDescDubboService {

    /**
     * Search item description by primary key
     * @param id
     * @return
     */
    TbItemDesc selectById(Long id);
}

/**
 *CREATE TABLE `tb_item_desc` (
 *   `item_id` bigint NOT NULL COMMENT '商品ID',
 *   `item_desc` text COMMENT '商品描述', # recorded in html style
 *   `created` datetime DEFAULT NULL COMMENT '创建时间',
 *   `updated` datetime DEFAULT NULL COMMENT '更新时间',
 *   PRIMARY KEY (`item_id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品描述表';
 */