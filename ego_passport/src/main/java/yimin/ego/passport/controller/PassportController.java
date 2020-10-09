package yimin.ego.passport.controller;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.passport.service.PassportService;
import yimin.ego.pojo.TbUser;

import javax.servlet.http.HttpSession;


/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/6 18:21
 *   @Description :
 *
 */
@Controller
public class PassportController {

    @Autowired
    private PassportService passportService;

    /**
     * Display login page
     * @return
     */
    @RequestMapping("/user/showLogin")
    public String showLogin(){
        return "login";
    }

    /**
     * Display register page
     * @return
     */
    @RequestMapping("/user/showRegister")
    public String showRegister(){
        return "register";
    }

    /**
     * check whether username, phone, email are already exist
     * @param param
     * @param type
     * @return
     */
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public EgoResult check(@PathVariable String param, @PathVariable int type){
        TbUser tbUser = new TbUser();
        if(type == 1){
            tbUser.setUsername(param);
        } else if(type == 2){
            tbUser.setPhone(param);
        } else if(type == 3){
            tbUser.setEmail(param);
        }
        return passportService.check(tbUser);
    }

    /**
     * register
     * @param tbUser
     * @param pwdRepeat
     * @return
     */
    @RequestMapping("/user/register")
    @ResponseBody
    public EgoResult register(TbUser tbUser, String pwdRepeat){

        // User names can only contain letters and Numbers, and 6-12 digits
        if (Strings.isEmpty(tbUser.getUsername())||!tbUser.getUsername().matches("^[a-zA-Z0-9]{6,12}$")){
            return EgoResult.error("User names can only contain letters and Numbers, and 6-12 digits");
        }
        if(Strings.isEmpty(tbUser.getEmail()) || !tbUser.getEmail().matches("^[az\\d]+(\\.[a-z\\d]+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$")){
            return EgoResult.error("Email format is invalided");
        }
        if(Strings.isEmpty(tbUser.getPassword())||!tbUser.getPassword().matches("^[a-zA-Z0-9]{6,12}$")){
            return EgoResult.error("Password names can only contain letters and Numbers, and 6-12 digits");
        }
        if(Strings.isEmpty(pwdRepeat)||!pwdRepeat.equals(tbUser.getPassword()))
        {
            return EgoResult.error("Inconsistent passwords");
        }
        if(Strings.isEmpty(tbUser.getPhone())||!tbUser.getPhone().matches("^1\\d{10}$")){
            return EgoResult.error("The phone number is invalid");
        }
        return passportService.register(tbUser);
    }


    /**
     * Login
     * @param tbUser
     * @param session
     * @return
     */
    @RequestMapping("/user/login")
    @ResponseBody
    public EgoResult login(TbUser tbUser, HttpSession session){
        EgoResult er = passportService.login(tbUser);
        if(er.getStatus() == 200){
            session.setAttribute("loginUser", er.getData());
        }
        return er;
    }

    /**
     * Acquire user's information
     * @param session
     * @return
     */
    @RequestMapping("/user/token/{token}")
    @ResponseBody
    @CrossOrigin
    public EgoResult token(HttpSession session){
        Object object = session.getAttribute("loginUser");
        if(object != null){
            TbUser tbUser = (TbUser)object;
            tbUser.setPassword("");
            return EgoResult.ok(tbUser);
        }
        return EgoResult.error("login failed");
    }

    /**
     * Logout
     */
    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    @CrossOrigin(allowCredentials = "true")
    public EgoResult logout(HttpSession session){
        session.invalidate();
        return EgoResult.ok();
    }

    /**
     * redirect to the original main index
     */
    @RequestMapping("/user/showLogin")
    public String showLogin(@RequestHeader(name="Referer", required = false) String referer, Model model){
        if(Strings.isNotEmpty(referer)){
            if(!referer.endsWith("/user/showRegister")){
                model.addAttribute("redirect", referer);
            }
        }
        return "login";
    }
}
