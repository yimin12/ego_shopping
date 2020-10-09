package yimin.ego.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 16:59
 *   @Description :
 *      The return type should be map, because Kindeditor will return different when it upload success and fail
 */
public interface PicService {


    /**
     * upload the file from consumer (front end system)
     * @param file
     * @return
     */
    Map<String, Object> update(MultipartFile file);

}
