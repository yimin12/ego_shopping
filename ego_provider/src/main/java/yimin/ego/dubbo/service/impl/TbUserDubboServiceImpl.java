package yimin.ego.dubbo.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import yimin.ego.dubbo.service.TbUserDubboService;
import yimin.ego.mapper.TbUserMapper;
import yimin.ego.pojo.TbUser;
import yimin.ego.pojo.TbUserExample;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 19:17
 *   @Description :
 *
 */
@Service
public class TbUserDubboServiceImpl implements TbUserDubboService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser selectByUser(TbUser tbUser) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if(tbUser.getUsername() != null){
            criteria.andUsernameEqualTo(tbUser.getUsername());
        } else if(tbUser.getEmail() != null){
            criteria.andEmailEqualTo(tbUser.getEmail());
        } else if(tbUser.getPhone() != null){
            criteria.andPhoneEqualTo(tbUser.getPhone());
        }
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insert(TbUser tbUser) {
        return tbUserMapper.insert(tbUser);
    }

    @Override
    public TbUser selectByUsernamePwd(TbUser tbUser) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
