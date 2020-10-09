package yimin.ego.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import yimin.ego.commons.exception.DaoException;
import yimin.ego.commons.pojo.EasyUITree;
import yimin.ego.commons.pojo.EgoResult;
import yimin.ego.commons.utils.IDUtils;
import yimin.ego.dubbo.service.TbContentCategoryDubboService;
import yimin.ego.pojo.TbContentCategory;
import yimin.ego.service.TbContentCategoryService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 12:27
 *   @Description :
 *
 */
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboService;

    @Override
    public List<EasyUITree> showContentCategory(Long pid) {
        List<TbContentCategory> list = tbContentCategoryDubboService.selectByPid(pid);
        List<EasyUITree> listTree = new ArrayList<>();
        for(TbContentCategory category:list){
            EasyUITree tree = new EasyUITree();
            tree.setId(category.getId());
            tree.setState(category.getIsParent() ? "closed" : "open");
            tree.setText(category.getName());
            listTree.add(tree);
        }
        return listTree;
    }

    @Override
    public EgoResult insert(TbContentCategory tbContentCategory) {
        Date date = new Date();
        tbContentCategory.setUpdated(date);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setCreated(date);
        tbContentCategory.setId(IDUtils.genItemId());
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        try{
            int index = tbContentCategoryDubboService.insert(tbContentCategory);
            if(index == 1){
                return EgoResult.ok(tbContentCategory);
            }
        } catch (DaoException e){
            e.printStackTrace();
        }
        return EgoResult.error("updated failed");
    }

    @Override
    public EgoResult update(TbContentCategory tbContentCategory) {
        tbContentCategory.setUpdated(new Date());
        int index = tbContentCategoryDubboService.updateNameById(tbContentCategory);
        if(index == 1){
            return EgoResult.ok();
        }
        return EgoResult.error("Rename failed");
    }

    @Override
    public EgoResult delete(Long id) {
        try{
            int index = tbContentCategoryDubboService.deleteById(id);
            if(index == 1){
                return EgoResult.ok();
            }
        } catch (DaoException exception){
            exception.printStackTrace();
        }
        return EgoResult.error("delete failed");
    }
}
