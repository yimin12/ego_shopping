package yimin.ego.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yimin.ego.commons.exception.DaoException;
import yimin.ego.commons.pojo.EasyUIDatagrid;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.commons.utils.IDUtils;
import yimin.ego.dubbo.service.TbContentDubboService;
import yimin.ego.pojo.TbContent;
import yimin.ego.sender.Send;
import yimin.ego.service.TbContentService;

import java.util.Date;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 13:39
 *   @Description :
 *
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Reference
    private TbContentDubboService tbContentDubboService;

    @Autowired
    private Send send;

    @Value("${ego.rabbitmq.content.queueName}")
    private String contentQueue;

    @Value("${ego.bigad.categoryId}")
    private Long bigadId;

    @Override
    public EasyUIDatagrid showContent(Long categoryId, int page, int rows) {
        List<TbContent> list = tbContentDubboService.selectByPage(categoryId, page, rows);
        long total = tbContentDubboService.selectCountByCategoryId(categoryId);
        return new EasyUIDatagrid(list, total);
    }

    @Override
    public EgoResult insert(TbContent tbContent) {
        tbContent.setId(IDUtils.genItemId());
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        int index = tbContentDubboService.insert(tbContent);
        if(index == 1){
            // when you update the info of database successful, send it to rabbitmq
            if(tbContent.getCategoryId().equals(bigadId)){
                send.send(contentQueue, "async");
            }
            System.out.println("Sending the message to rabbitmq");
            return EgoResult.ok();
        }
        return EgoResult.error("insert failed");
    }

    @Override
    public EgoResult update(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        int index = tbContentDubboService.update(tbContent);
        if(index == 1){
            if(tbContent.getCategoryId().equals(bigadId)){
                send.send(contentQueue,"async");
            }
            return EgoResult.ok();
        }
        return EgoResult.error("update failed");
    }

    @Override
    public EgoResult delete(long[] ids) {
        try{
            int index = tbContentDubboService.deleteByIds(ids);
            if(index == 1){
                send.send(contentQueue, "async");
                return EgoResult.ok();
            }
        } catch (DaoException exception) {
            exception.printStackTrace();
        }
        return EgoResult.error("deleted failed");
    }
}
