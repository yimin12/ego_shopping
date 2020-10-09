package yimin.ego.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import yimin.ego.dubbo.service.TbItemCatDubboService;
import yimin.ego.mapper.TbItemCatMapper;
import yimin.ego.pojo.TbItemCat;
import yimin.ego.pojo.TbItemCatExample;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 17:18
 *   @Description :
 *
 */
@Service
public class TbItemCatDubboServiceImpl implements TbItemCatDubboService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<TbItemCat> selectByPid(Long pid) {
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(pid);
        return tbItemCatMapper.selectByExample(example);
    }

    @Override
    public TbItemCat selectById(Long id) {
        return tbItemCatMapper.selectByPrimaryKey(id);
    }
}
