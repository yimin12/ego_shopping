package yimin.ego.portal.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yimin.ego.commons.pojo.BigAd;
import yimin.ego.commons.utils.JsonUtils;
import yimin.ego.dubbo.service.TbContentDubboService;
import yimin.ego.pojo.TbContent;
import yimin.ego.portal.service.PortalService;

import java.util.ArrayList;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 22:47
 *   @Description :
 *
 */
@Service
public class PortalServiceImpl implements PortalService {

    @Reference
    private TbContentDubboService tbContentDubboService;
    @Value("${ego.bigad.categoryId}")
    private Long categoryId;

    /**
     * Two Apis for Cacheable and CachePut
     * @return
     */
    @Override
    @Cacheable(cacheNames = "yimin.ego.protal", key = "'bigad'")
    public String showBigAd() {
        System.out.println("executed showBigAd");
        List<TbContent> list = tbContentDubboService.selectAllByCategoryId(categoryId);
        List<BigAd> listBigad = new ArrayList<>();
        for(TbContent tbContent:list){
            BigAd bigAd = new BigAd();
            bigAd.setAlt("");
            bigAd.setHeight(240);
            bigAd.setHeightB(240);
            bigAd.setHref(tbContent.getUrl());
            bigAd.setSrc(tbContent.getPic());
            bigAd.setSrcB(tbContent.getPic2());
            bigAd.setWidth(670);
            bigAd.setWidthB(550);
            listBigad.add(bigAd);
        }
        return JsonUtils.objectToJson(listBigad);
    }

    @Override
    @CachePut(cacheNames = "yimin.ego.portal", key = "'bigad'")
    public String showBigAd2() {
        System.out.println("执行");
        List<TbContent> list = tbContentDubboService.selectAllByCategoryId(categoryId);
        System.out.println("size:"+list.size());
        List<BigAd> listBigad = new ArrayList<>();
        for(TbContent tbContent: list){
            BigAd bigAd = new BigAd();
            bigAd.setAlt("");
            bigAd.setHeight(240);
            bigAd.setHeightB(240);
            bigAd.setHref(tbContent.getUrl());
            bigAd.setSrc(tbContent.getPic());
            bigAd.setSrcB(tbContent.getPic2());
            bigAd.setWidth(670);
            bigAd.setWidthB(550);
            listBigad.add(bigAd);
        }
        return JsonUtils.objectToJson(listBigad);
    }
}
