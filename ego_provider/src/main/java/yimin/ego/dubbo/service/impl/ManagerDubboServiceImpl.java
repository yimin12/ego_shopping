package yimin.ego.dubbo.service.impl;

import yimin.ego.dubbo.service.ManagerDubboService;
import yimin.ego.mapper.ManagerMapper;
import yimin.ego.pojo.Manager;
import yimin.ego.pojo.ManagerExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/9/30 21:14
 *   @Description :
 *
 */

@Service
public class ManagerDubboServiceImpl implements ManagerDubboService {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public Manager selectManagerByUsername(String username) {
        ManagerExample example = new ManagerExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<Manager> list = managerMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

}
