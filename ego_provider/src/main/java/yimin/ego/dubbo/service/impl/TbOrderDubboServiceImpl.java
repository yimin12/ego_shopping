package yimin.ego.dubbo.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import yimin.ego.commons.exception.DaoException;
import yimin.ego.dubbo.service.TbOrderDubboService;
import yimin.ego.mapper.TbItemMapper;
import yimin.ego.mapper.TbOrderItemMapper;
import yimin.ego.mapper.TbOrderMapper;
import yimin.ego.mapper.TbOrderShippingMapper;
import yimin.ego.pojo.TbItem;
import yimin.ego.pojo.TbOrder;
import yimin.ego.pojo.TbOrderItem;
import yimin.ego.pojo.TbOrderShipping;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/7 13:06
 *   @Description :
 *
 */
@Service
public class TbOrderDubboServiceImpl implements TbOrderDubboService {

    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbOrder tbOrder, List<TbOrderItem> list, TbOrderShipping tbOrderShipping) throws DaoException {
        int index = tbOrderMapper.insertSelective(tbOrder);
        if(index == 1){
            int index2 = 0;
            int index3 = 0;
            for(TbOrderItem tbOrderItem : list){
                index2 += tbOrderItemMapper.insert(tbOrderItem);
                Long itemId = Long.parseLong(tbOrderItem.getItemId());
                TbItem tbItemDB = tbItemMapper.selectByPrimaryKey(itemId);
                // update the remaining in database
                TbItem tbItem = new TbItem();
                tbItem.setId(Long.parseLong(tbOrderItem.getItemId()));
                tbItem.setNum(tbItemDB.getNum() - tbOrderItem.getNum());
                tbItem.setUpdated(tbOrder.getCreateTime());
                index3 += tbItemMapper.updateByPrimaryKey(tbItem);
            }
            if(index2 == list.size() && index3 == list.size()){
                int index4 = tbOrderShippingMapper.insertSelective(tbOrderShipping);
                if(index4 == 1){
                    return 1;
                }
            }
        }
        throw new DaoException("update database failed");
    }
}
