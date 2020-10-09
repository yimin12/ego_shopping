package yimin.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import yimin.ego.service.PicService;

import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 17:12
 *   @Description :
 *
 */
@Controller
public class PicController {

    @Autowired
    private PicService picService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map<String,Object> update(MultipartFile uploadFile){
        return picService.update(uploadFile);
    }
}
