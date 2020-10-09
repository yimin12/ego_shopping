package yimin.ego.dubbo.service;

import yimin.ego.commons.exception.DaoException;
import yimin.ego.pojo.TbContent;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 13:29
 *   @Description :
 *
 */
public interface TbContentDubboService {

    /**
     * Search content by given category id within paging range
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<TbContent> selectByPage(Long categoryId, int pageNumber, int
            pageSize);

    /**
     * Count the number of content by given category id
     * @param categoryId
     * @return
     */
    long selectCountByCategoryId(Long categoryId);

    /**
     * Insert an content
     * @param tbContent
     * @return
     */
    int insert(TbContent tbContent);

    /**
     * Update an content
     * @param tbContent
     * @return
     */
    int update(TbContent tbContent);

    /**
     * Delete contents by given category id
     * @param ids
     * @return
     * @throws DaoException
     */
    int deleteByIds(long[] ids) throws DaoException;

    /**
     * Search all contents by given category id, sorted in time sequence
     * @param categoryId
     * @return
     */
    List<TbContent> selectAllByCategoryId(Long categoryId);

    /**
     * Search content by primary key
     * @param id
     * @return
     */
    TbContent selectById(Long id);
}

/**
 * CREATE TABLE `tb_content` (
 *   `id` bigint NOT NULL AUTO_INCREMENT,
 *   `category_id` bigint NOT NULL COMMENT '内容类目ID',
 *   `title` varchar(200) DEFAULT NULL COMMENT '内容标题',
 *   `sub_title` varchar(100) DEFAULT NULL COMMENT '子标题',
 *   `title_desc` varchar(500) DEFAULT NULL COMMENT '标题描述',
 *   `url` varchar(500) DEFAULT NULL COMMENT '链接',
 *   `pic` varchar(300) DEFAULT NULL COMMENT '图片绝对路径',
 *   `pic2` varchar(300) DEFAULT NULL COMMENT '图片2',
 *   `content` text COMMENT '内容',
 *   `created` datetime DEFAULT NULL,
 *   `updated` datetime DEFAULT NULL,
 *   PRIMARY KEY (`id`),
 *   KEY `category_id` (`category_id`),
 *   KEY `updated` (`updated`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
 */
