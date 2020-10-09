package yimin.ego.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.dubbo.service.TbItemParamItemDubboService;
import yimin.ego.pojo.TbItemParamItem;
import yimin.ego.service.TbItemParamItemService;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 12:59
 *   @Description :
 *
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;

    @Override
    public EgoResult showItemParamItem(Long itemId) {
        TbItemParamItem tbItemParamItem = tbItemParamItemDubboService.selectByItemId(itemId);
        if(tbItemParamItem != null){
            return EgoResult.ok(tbItemParamItem);
        }
        return EgoResult.error("query failed");
    }
}
