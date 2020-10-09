package yimin.ego.passport.service;

import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.pojo.TbUser;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 19:26
 *   @Description :
 *
 */
public interface PassportService {

    /**
     * check whether the user exist
     */
    EgoResult check(TbUser tbUser);

    /**
     * insert the user to database
     */
//    EgoResult insert(TbUser tbUser);

    /**
     * register an new user
     */
    EgoResult register(TbUser tbUser);

    /**
     * login function
     */
    EgoResult login(TbUser tbUser);


}
