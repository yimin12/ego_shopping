package yimin.ego.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import yimin.ego.commons.pojo.EasyUITree;
import yimin.ego.dubbo.service.TbItemCatDubboService;
import yimin.ego.pojo.TbItemCat;
import yimin.ego.service.TbItemCatService;

import java.util.ArrayList;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 17:28
 *   @Description :
 *
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    @Override
    public List<EasyUITree> showTree(Long pid) {
        List<EasyUITree> listTree = new ArrayList<>();
        List<TbItemCat> list = tbItemCatDubboService.selectByPid(pid);
        for(TbItemCat cat: list){
            EasyUITree tree = new EasyUITree();
            tree.setId(cat.getId());
            tree.setText(cat.getName());
            tree.setState(cat.getIsParent() ? "closed":"Open");
            listTree.add(tree);
        }
        return listTree;
    }
}
