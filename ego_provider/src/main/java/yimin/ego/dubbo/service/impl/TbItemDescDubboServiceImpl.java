package yimin.ego.dubbo.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import yimin.ego.dubbo.service.TbItemDescDubboService;
import yimin.ego.mapper.TbItemDescMapper;
import yimin.ego.pojo.TbItemDesc;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 19:12
 *   @Description :
 *
 */
@Service
public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TbItemDesc selectById(Long id) {
        return tbItemDescMapper.selectByPrimaryKey(id);
    }
}
