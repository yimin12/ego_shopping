package yimin.ego.dubbo.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import yimin.ego.dubbo.service.TbItemParamItemDubboService;
import yimin.ego.mapper.TbItemParamItemMapper;
import yimin.ego.pojo.TbItemParamItem;
import yimin.ego.pojo.TbItemParamItemExample;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 13:04
 *   @Description :
 *
 */
@Service
public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public TbItemParamItem selectByItemId(Long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list != null && list.size() == 1){
            return list.get(0);
        }
        return null;
    }
}
