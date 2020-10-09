package yimin.ego.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.dubbo.service.TbItemDescDubboService;
import yimin.ego.pojo.TbItemDesc;
import yimin.ego.service.TbItemDescService;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 19:18
 *   @Description :
 *
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Override
    public EgoResult selectById(Long id) {
        TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(id);
        return EgoResult.ok(tbItemDesc);
    }
}
