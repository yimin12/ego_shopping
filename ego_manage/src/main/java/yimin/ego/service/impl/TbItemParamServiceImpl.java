package yimin.ego.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import yimin.ego.commons.exception.DaoException;
import yimin.ego.commons.pojo.EasyUIDatagrid;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.commons.utils.IDUtils;
import yimin.ego.dubbo.service.TbItemCatDubboService;
import yimin.ego.dubbo.service.TbItemParamDubboService;
import yimin.ego.pojo.TbItemParam;
import yimin.ego.pojo.TbItemParamChild;
import yimin.ego.service.TbItemParamService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 22:46
 *   @Description :
 *
 */
@Service
public class TbItemParamServiceImpl implements TbItemParamService {

    @Reference
    private TbItemParamDubboService tbItemParamDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    @Override
    public EasyUIDatagrid showItemParam(int page, int rows) {
        List<TbItemParam> list = tbItemParamDubboService.selectByPage(page, rows);
        List<TbItemParamChild> listChild = new ArrayList<>();
        for(TbItemParam param:list){
            TbItemParamChild child = new TbItemParamChild();
            BeanUtils.copyProperties(param, child);
            child.setItemCatName(tbItemCatDubboService.selectById(param.getItemCatId())
                    .getName());
            listChild.add(child);
        }
        long total = tbItemParamDubboService.selectCount();
        return new EasyUIDatagrid(listChild, total);
    }

    @Override
    public EgoResult showItemParamByCatid(Long catId) {
        TbItemParam tbItemParam = tbItemParamDubboService.selectByCatId(catId);
        if(tbItemParam != null){
            return EgoResult.ok(tbItemParam);
        }
        return EgoResult.error("failed");
    }

    @Override
    public EgoResult insert(TbItemParam tbItemParam) {
        tbItemParam.setId(IDUtils.genItemId());
        Date date = new Date();
        tbItemParam.setCreated(date);
        tbItemParam.setUpdated(date);
        int index = tbItemParamDubboService.insert(tbItemParam);
        if(index==1){
            return EgoResult.ok();
        }
        return EgoResult.error("update failed");
    }

    @Override
    public EgoResult delete(long[] ids) {
        try {
            int index = tbItemParamDubboService.delete(ids);
            if(index==1){
                return EgoResult.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResult.error("删除失败");
    }

}
