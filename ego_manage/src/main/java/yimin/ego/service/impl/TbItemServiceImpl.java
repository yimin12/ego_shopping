package yimin.ego.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yimin.ego.commons.exception.DaoException;
import yimin.ego.commons.pojo.EasyUIDatagrid;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.commons.utils.IDUtils;
import yimin.ego.dubbo.service.TbItemDubboService;
import yimin.ego.pojo.TbItem;
import yimin.ego.pojo.TbItemDesc;
import yimin.ego.pojo.TbItemParamItem;
import yimin.ego.sender.Send;
import yimin.ego.service.TbItemService;

import java.util.Date;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 10:43
 *   @Description :
 *
 */
@Service
public class TbItemServiceImpl implements TbItemService {


    @Reference
    private TbItemDubboService tbItemDubboService;
    @Autowired
    private Send send;
    @Value("${ego.rabbitmq.item.insertName}")
    private String itemInsert;
    @Value("${ego.rabbitmq.item.deleteName}")
    private String itemDelete;

    @Override
    public EasyUIDatagrid showItem(int page, int rows) {
        List<TbItem> list = tbItemDubboService.selectByPage(rows, page);
        long total = tbItemDubboService.selectCount();
        return new EasyUIDatagrid(list, total);
    }

    @Override
    public EgoResult updateStatus(long[] ids, int status) {
        try {
            int index = tbItemDubboService.updateStatusByIds(ids, status);
            if (index == 1) {
                if(status==1){
                    // update in solr
                    send.send(itemInsert, StringUtils.join(ids,','));
                }else if(status==2 || status ==3){
                    send.send(itemDelete,StringUtils.join(ids,','));
                }
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("operation failed");
    }

    @Override
    public EgoResult insert(TbItem item, String desc) {
        Date date = new Date();
        long id = IDUtils.genItemId();

        item.setCreated(date);
        item.setUpdated(date);

        item.setId(id);
        item.setStatus((byte)1);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(date);
        tbItemDesc.setCreated(date);

        try{
            int index = tbItemDubboService.insert(item, tbItemDesc);
            if(index == 1){
                send.send(itemInsert, id);
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("fail to add new item");
    }

    @Override
    public EgoResult update(TbItem item, String desc) {
        Date date = new Date();
        item.setUpdated(date);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(item.getId());
        try {
            int index = tbItemDubboService.update(item, tbItemDesc);
            if(index==1){
                send.send(itemInsert,item.getId());
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("update failed");
    }

    @Override
    public EgoResult insert(TbItem item, String desc,String itemParams) {
        Date date = new Date();
        long id = IDUtils.genItemId();
        // item's data
        item.setCreated(date);
        item.setUpdated(date);
        item.setId(id);
        item.setStatus((byte)1);
        // item's description
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(date);
        tbItemDesc.setCreated(date);
        // item's parameter
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setId(IDUtils.genItemId());
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        tbItemParamItem.setItemId(id);
        tbItemParamItem.setParamData(itemParams);
        try {
            int index = tbItemDubboService.insert(item,
                    tbItemDesc,tbItemParamItem);
            if(index==1){
                send.send(itemInsert,id);
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("update failed");
    }

    @Override
    public EgoResult update(TbItem item, String desc,String itemParams,long
            itemParamId) {
        Date date = new Date();
        item.setUpdated(date);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(item.getId());
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setId(itemParamId);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(date);
        try {
            int index = tbItemDubboService.update(item,tbItemDesc,tbItemParamItem);
            if(index==1){
                send.send(itemInsert,item.getId());
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("update failed");
    }


}
