package yimin.ego.dubbo.service;

import yimin.ego.commons.exception.DaoException;
import yimin.ego.pojo.TbItemParam;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 21:22
 *   @Description :
 *
 */
public interface TbItemParamDubboService {

    /**
     * search item parameters by paging limit
     * @param pageNumber
     * @param pageSize
     * @return data of current page
     */
    List<TbItemParam> selectByPage(int pageNumber, int pageSize);

    /**
     * Search total number of item parameters
     * @return
     */
    long selectCount();


    /**
     * Search item parameters by category id
     * @param catId
     * @return
     */
    TbItemParam selectByCatId(Long catId);

    /**
     * Insert
     * @param tbItemParam
     * @return
     */
    int insert(TbItemParam tbItemParam);

    /**
     * Batch delete
     * @param ids
     * @return
     */
    int delete(long[] ids) throws DaoException;
}

/**
 * CREATE TABLE `tb_item_param` (
 *   `id` bigint NOT NULL AUTO_INCREMENT,
 *   `item_cat_id` bigint DEFAULT NULL COMMENT '商品类目ID',
 *   `param_data` text COMMENT '参数数据，格式为json格式',
 *   `created` datetime DEFAULT NULL,
 *   `updated` datetime DEFAULT NULL,
 *   PRIMARY KEY (`id`),
 *   KEY `item_cat_id` (`item_cat_id`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='商品规则参数';
 */
