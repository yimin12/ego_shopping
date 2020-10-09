package yimin.ego.cart.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import yimin.ego.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/7 12:07
 *   @Description :
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${ego.passport.loginUrl}")
    private String loginUrl;


    /**
     * before advice:
     *  We should do it before every login
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // whether the user has login in
        TbUser tbUser = (TbUser) request.getSession().getAttribute("loginUser");
        if(tbUser != null){
            System.out.println("already login");
            return true;
        }
        response.sendRedirect(loginUrl);
        return false;
    }
}
