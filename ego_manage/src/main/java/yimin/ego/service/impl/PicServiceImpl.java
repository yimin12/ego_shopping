package yimin.ego.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yimin.ego.commons.utils.FastDFSClient;
import yimin.ego.service.PicService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 17:02
 *   @Description :
 *
 */
@Service
public class PicServiceImpl implements PicService {

    @Value("${ego.fastdfs.nginx}")
    private String nginxHost;

    @Override
    public Map<String, Object> update(MultipartFile file) {

        Map<String, Object> map = new HashMap<>();
        try{
            /**
             * file.getInputStream() : get the picture's inputStream
             * file.getOriginalFilename() : get the fileName of upload file
             * FastDFS  file will be renamed by the FastDFS
             */
            String[] result = FastDFSClient.uploadFile(file.getInputStream(), file.getOriginalFilename());
            map.put("error", 0);
            map.put("url", nginxHost+result[0]+"/"+result[1]);
            return map;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        map.put("error", 1);
        map.put("message", "This is message");
        return map;
    }
}
