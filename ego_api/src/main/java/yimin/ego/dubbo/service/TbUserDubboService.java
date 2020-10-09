package yimin.ego.dubbo.service;

import yimin.ego.pojo.TbUser;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 19:15
 *   @Description :
 *
 */
public interface TbUserDubboService {

    /**
     * Search user (dynamically SQL)
     * @param tbUser
     * @return
     */
    TbUser selectByUser(TbUser tbUser);


    /**
     * Insert an new User
     * @param tbUser
     * @return
     */
    int insert(TbUser tbUser);

    /**
     * Login by given username and password
     * @param tbUser
     * @return
     */
    TbUser selectByUsernamePwd(TbUser tbUser);

}

/**
 * CREATE TABLE `tb_user` (
 *   `id` bigint NOT NULL AUTO_INCREMENT,
 *   `username` varchar(50) NOT NULL COMMENT '用户名',
 *   `password` varchar(32) NOT NULL COMMENT '密码，加密存储',
 *   `phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
 *   `email` varchar(50) DEFAULT NULL COMMENT '注册邮箱',
 *   `created` datetime NOT NULL,
 *   `updated` datetime NOT NULL,
 *   PRIMARY KEY (`id`),
 *   UNIQUE KEY `username` (`username`) USING BTREE,
 *   UNIQUE KEY `phone` (`phone`) USING BTREE,
 *   UNIQUE KEY `email` (`email`) USING BTREE
 * ) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='用户表';
 */
