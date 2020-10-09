package yimin.ego.dubbo.service;

import yimin.ego.pojo.Manager;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/9/30 20:32
 *   @Description :
 *
 *      manipulations for manager tables in my database
 *
 */
public interface ManagerDubboService {

    /**
     * Search the manager by given name;
     * @param username
     * @return
     */
    Manager selectManagerByUsername(String username);

}

/**
 * CREATE TABLE `manager` (
 *   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
 *   `username` varchar(20) NOT NULL COMMENT '账号',
 *   `password` varchar(100) NOT NULL COMMENT '密码,bcrype加密',
 *   PRIMARY KEY (`id`),
 *   UNIQUE KEY `username` (`username`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台账号';
 */
