package yimin.ego.dubbo.service;

import yimin.ego.pojo.TbItemCat;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 17:15
 *   @Description :
 *
 */
public interface TbItemCatDubboService {

    /**
     * search all categories by given parent id
     * @param pid
     * @return all sub categories
     */
    List<TbItemCat> selectByPid(Long pid);

    /**
     * Search category by primary key
     * @param id
     * @return
     */
    TbItemCat selectById(Long id);
}

/**
 * CREATE TABLE `tb_item_cat` (
 *   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '类目ID',
 *   `parent_id` bigint DEFAULT NULL COMMENT '父类目ID=0时，代表的是一级的类目',
 *   `name` varchar(50) DEFAULT NULL COMMENT '类目名称',
 *   `status` int DEFAULT '1' COMMENT '状态。可选值:1(正常),2(删除)',
 *   `sort_order` int DEFAULT NULL COMMENT '排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数',
 *   `is_parent` tinyint(1) DEFAULT '1' COMMENT '该类目是否为父类目，1为true，0为false',
 *   `created` datetime DEFAULT NULL COMMENT '创建时间',
 *   `updated` datetime DEFAULT NULL COMMENT '创建时间',
 *   PRIMARY KEY (`id`),
 *   KEY `parent_id` (`parent_id`,`status`) USING BTREE,
 *   KEY `sort_order` (`sort_order`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=1183 DEFAULT CHARSET=utf8 COMMENT='商品类目';
 */
