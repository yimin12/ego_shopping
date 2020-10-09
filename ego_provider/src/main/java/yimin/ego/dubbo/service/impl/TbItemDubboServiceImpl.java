package yimin.ego.dubbo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import yimin.ego.commons.exception.DaoException;
import yimin.ego.dubbo.service.TbItemDubboService;
import yimin.ego.mapper.TbItemDescMapper;
import yimin.ego.mapper.TbItemMapper;
import yimin.ego.mapper.TbItemParamItemMapper;
import yimin.ego.pojo.TbItem;
import yimin.ego.pojo.TbItemDesc;
import yimin.ego.pojo.TbItemParamItem;

import java.util.Date;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 10:29
 *   @Description :
 *
 */
@Service
public class TbItemDubboServiceImpl implements TbItemDubboService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public List<TbItem> selectByPage(int pageSize, int pageNumber) {
        // the page plugins should write above the query
        PageHelper.startPage(pageNumber, pageSize);
        List<TbItem> list = tbItemMapper.selectByExample(null);
        // Example 相当于sql 中where,没有where 时参数为null 即可
        // select * from tb_item limit ?,?
        PageInfo<TbItem> pi = new PageInfo<>(list);
        return pi.getList();
    }

    @Override
    public long selectCount() {

        return tbItemMapper.countByExample(null);

    }

    @Override
    @Transactional // rollback when it encounters exception
    public int updateStatusByIds(long[] ids, int status) throws DaoException {
        int index = 0;
        Date date = new Date();
        for(long id:ids){
            TbItem tbItem = new TbItem();
            tbItem.setId(id);
            tbItem.setStatus((byte)status);
            tbItem.setUpdated(date);
            index += tbItemMapper.updateByPrimaryKeySelective(tbItem); // success will return 1, else return 0
        }
        if(index == ids.length){
            return 1;
        }
        throw new DaoException("batch update fail");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbItem tbItem, TbItemDesc tbItemDesc) throws DaoException {
        int index = tbItemMapper.insert(tbItem);
        if(index == 1){
            int index2 = tbItemDescMapper.insert(tbItemDesc);
            index2 = 0;
            if(index2 == 1){
                return 1;
            }
        }
        throw new DaoException("add new items fails");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(TbItem tbItem, TbItemDesc tbItemDesc) throws DaoException {
        int index = tbItemMapper.updateByPrimaryKeySelective(tbItem);
        if(index == 1){
            int index2 = tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
            if(index == 1){
                return 1;
            }
        }
        throw new DaoException("update failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem
            tbItemParamItem) throws DaoException {
        int index = tbItemMapper.insert(tbItem);
        if(index==1){
            int index2 = tbItemDescMapper.insert(tbItemDesc);
            if(index2==1){
                int index3 = tbItemParamItemMapper.insert(tbItemParamItem);
                if(index3==1){
                    return 1;
                }
            }
        }
        throw new DaoException("update failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem
            tbItemParamItem) throws DaoException {
        // 一定要调用selective，动态sql
        int index = tbItemMapper.updateByPrimaryKeySelective(tbItem);
        if(index==1){
            int index2 =
                    tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
            if(index2==1){
                int index3 =
                        tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
                if(index3==1){
                    return 1;
                }
            }
        }
        throw new DaoException("修改失败");
    }

    @Override
    public TbItem selectById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }
}
