package yimin.ego.service.impl;

import yimin.ego.dubbo.service.ManagerDubboService;
import yimin.ego.pojo.Manager;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/9/30 21:33
 *   @Description :
 *
 */

@Service
public class LoginServiceImpl implements UserDetailsService {

    @Reference
    private ManagerDubboService managerDubboService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = managerDubboService.selectManagerByUsername(username);
        if (manager==null){
            throw new UsernameNotFoundException("User does not exist");
        }

        return new User(username,manager.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("not contains authority"));
    }
}
