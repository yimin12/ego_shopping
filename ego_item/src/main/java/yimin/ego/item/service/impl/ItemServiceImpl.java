package yimin.ego.item.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yimin.ego.commons.pojo.TbItemDetails;
import yimin.ego.commons.utils.JsonUtils;
import yimin.ego.dubbo.service.TbItemCatDubboService;
import yimin.ego.dubbo.service.TbItemDescDubboService;
import yimin.ego.dubbo.service.TbItemDubboService;
import yimin.ego.dubbo.service.TbItemParamItemDubboService;
import yimin.ego.item.pojo.CategoryNode;
import yimin.ego.item.pojo.ItemCategoryNav;
import yimin.ego.item.pojo.ParamItem;
import yimin.ego.item.service.ItemService;
import yimin.ego.pojo.*;

import java.util.ArrayList;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 0:58
 *   @Description :
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;
    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;

    /**
     * Recursively get the all sub item category with helper function (getAllItemCat)
     * @return
     */
    @Override
    public ItemCategoryNav showItemCat() {
        ItemCategoryNav itemCategoryNav = new ItemCategoryNav();
        itemCategoryNav.setData(getAllItemCat(0L));
        return itemCategoryNav;
    }

    /**
     * Use SpringCache (Redis) to store the items you have visited before.
     * @param id
     * @return
     */
    @Override
    @Cacheable(cacheNames = "yimin.ego.item", key = "'details:'+#id")
    public TbItemDetails showItem(Long id) {
        TbItem tbItem = tbItemDubboService.selectById(id);
        TbItemDetails details = new TbItemDetails();
        details.setId(tbItem.getId());
        details.setPrice(tbItem.getPrice());
        details.setSellPoint(tbItem.getSellPoint());
        details.setTitle(tbItem.getTitle());
        String img = tbItem.getImage();
        details.setImages(img!=null && !img.equals("")?img.split(","):new String[1]);
        return details;
    }

    @Override
    public String showItemDesc(Long itemId) {
        TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(itemId);
        return tbItemDesc.getItemDesc();
    }

    @Override
    public String showItemParam(Long itemId) {
        TbItemParamItem tbItemParamItem = tbItemParamItemDubboService.selectByItemId(itemId);
        String json = tbItemParamItem.getParamData();
        List<ParamItem> list = JsonUtils.jsonToList(json, ParamItem.class);
        StringBuffer sf = new StringBuffer();
        for(ParamItem paramItem : list){
            // Regard paramItem as as a table
            sf.append("<table style='color:gray;' width='100%' cellpadding='5'>");
            for(int i = 0 ;i<paramItem.getParams().size();i++){
                sf.append("<tr>");
                if(i==0){// The first line show the category's info
                    sf.append("<td style='width:100px;text-align:right;'>"+paramItem.getGroup()+"</td>");
                }else{
                    sf.append("<td> </td>");// all info is empty apart from the first line。
                }
                sf.append("<td style='width:200px;text-align:right;'>"+paramItem.getParams().get(i).getK()+"</td>");
                sf.append("<td>"+paramItem.getParams().get(i).getV()+"</td>");
                sf.append("</tr>");
            }
            sf.append("</table>");
            sf.append("<hr style='color:gray;'/>");
        }
        return sf.toString();
    }

    private List<Object> getAllItemCat(Long parentId){
        List<TbItemCat> list =
                tbItemCatDubboService.selectByPid(parentId);
        List<Object> listResult = new ArrayList<>();
        // Each category mapping one menu category 。First and second layer are CategoryNode ，The third layer is purely string
        for(TbItemCat cat : list){
            // case1: first layer or second layer
            if(cat.getIsParent()){
                CategoryNode node = new CategoryNode();
                node.setU("/products/"+cat.getId()+".html");
                node.setN("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
                node.setI(getAllItemCat(cat.getId()));
                listResult.add(node);
            }else{
                // the third layer
                listResult.add("/products/"+cat.getId()+".html|"+cat.getName());
            }
        }
        return listResult;
    }
}
