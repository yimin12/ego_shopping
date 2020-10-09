package yimin.ego.dubbo.service;

import yimin.ego.commons.exception.DaoException;
import yimin.ego.pojo.TbItem;
import yimin.ego.pojo.TbItemDesc;
import yimin.ego.pojo.TbItemParamItem;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 10:26
 *   @Description :
 *
 */
public interface TbItemDubboService {

    /**
     * Search Item by paging limit
     * @param pageSize
     * @param pageNumber
     * @return data of each page
     */
    List<TbItem> selectByPage(int pageSize, int pageNumber);

    /**
     * Search total items
     * @return
     */
    long selectCount();

    /**
     * transactions should be handled in provider modules
     * Batch transactions for update
     * @param ids
     * @param status
     * @return 1: success 0:fail
     */
    int updateStatusByIds(long[] ids, int status) throws DaoException;


    /**
     * Insert new item
     * @param tbItem
     * @param tbItemDesc
     * @return
     * @throws DaoException
     */
    int insert(TbItem tbItem, TbItemDesc tbItemDesc) throws DaoException;

    /**
     * Update item's information
     * @param tbItem
     * @param tbItemDesc
     * @return
     * @throws DaoException
     */
    int update(TbItem tbItem, TbItemDesc tbItemDesc) throws DaoException;

    /**
     * Insert new item with item param
     * @param tbItem
     * @param tbItemDesc
     * @param tbItemParamItem
     * @return
     * @throws DaoException
     */
    int insert(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws DaoException;

    /**
     * Update item's info with item param
     * @param tbItem
     * @param tbItemDesc
     * @param tbItemParamItem
     * @return
     * @throws DaoException
     */
    int update(TbItem tbItem,TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws DaoException;

    /**
     * Search by primary Key
     * @param id
     * @return
     */
    TbItem selectById(Long id);
}

/**
 * CREATE TABLE `tb_item` (
 *   `id` bigint NOT NULL COMMENT '商品id，同时也是商品编号',
 *   `title` varchar(100) NOT NULL COMMENT '商品标题',
 *   `sell_point` varchar(500) DEFAULT NULL COMMENT '商品卖点',
 *   `price` bigint NOT NULL COMMENT '商品价格，单位为：分',
 *   `num` int NOT NULL COMMENT '库存数量',
 *   `barcode` varchar(30) DEFAULT NULL COMMENT '商品条形码',
 *   `image` varchar(500) DEFAULT NULL COMMENT '商品图片',
 *   `cid` bigint NOT NULL COMMENT '所属类目，叶子类目',
 *   `status` tinyint NOT NULL DEFAULT '1' COMMENT '商品状态，1-正常，2-下架，3-删除',
 *   `created` datetime NOT NULL COMMENT '创建时间',
 *   `updated` datetime NOT NULL COMMENT '更新时间',
 *   PRIMARY KEY (`id`),
 *   KEY `cid` (`cid`),
 *   KEY `status` (`status`),
 *   KEY `updated` (`updated`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';
 */
