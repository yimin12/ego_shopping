package yimin.ego.dubbo.service.impl;

import com.google.gson.internal.$Gson$Preconditions;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import yimin.ego.commons.exception.DaoException;
import yimin.ego.dubbo.service.TbContentCategoryDubboService;
import yimin.ego.mapper.TbContentCategoryMapper;
import yimin.ego.pojo.TbContent;
import yimin.ego.pojo.TbContentCategory;
import yimin.ego.pojo.TbContentCategoryExample;

import java.util.Date;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 12:19
 *   @Description :
 *
 */
@Service
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> selectByPid(Long pid) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andStatusEqualTo(1).andParentIdEqualTo(pid);
        // sort it in ascending order
        example.setOrderByClause("sort_order asc");
        return tbContentCategoryMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbContentCategory category) throws DaoException {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andNameEqualTo(category.getName()).andStatusEqualTo(1);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        // no repeat name
        if(list != null && list.size() == 0){
            int index = tbContentCategoryMapper.insert(category);
            if(index == 1){
                // whether the is_parent is true
                TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(category.getParentId());
                if(!parentCategory.getIsParent()){
                    TbContentCategory parentUpdated = new TbContentCategory();
                    parentUpdated.setId(parentCategory.getId());
                    parentUpdated.setIsParent(true);
                    parentUpdated.setUpdated(category.getCreated());
                    int indexParent = tbContentCategoryMapper.updateByPrimaryKeySelective(parentUpdated);
                    if(indexParent != 1){
                        throw new DaoException("add new content - updated parent node failed");
                    }
                }
                return 1;
            }
        }
        throw new DaoException("add new content failed");
    }

    /**
     * If there are more than one DML operation, we must @Transaction and throw the Exception
     * If there are exactly one DML operation, it is ok to throw Exception only
     * @param tbContentCategory
     * @return
     */
    @Override
    public int updateNameById(TbContentCategory tbContentCategory) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andNameEqualTo(tbContentCategory.getName()).andStatusEqualTo(1);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        if(list != null && list.size() == 0){
            return tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor =  Exception.class)
    public int deleteById(Long id) throws DaoException {
        TbContentCategory category = new TbContentCategory();
        category.setId(id);
        Date date = new Date();
        category.setUpdated(date);
        category.setStatus(2);
        // logical delete
        int index = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
        if(index == 1){
            // Determines whether the parent of the current node has other children in a normal state.
            // Search the current node, you can query the current node's parent node
            TbContentCategory currContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(currContentCategory.getParentId()).andStatusEqualTo(1);
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
            if(list != null && list.size() == 0){
                TbContentCategory parent = new TbContentCategory();
                parent.setIsParent(false);
                parent.setId(currContentCategory.getParentId());
                parent.setUpdated(date);
                int indexParent = tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
                if(indexParent != 1){
                    throw new DaoException("delete category - modified parent node failed");
                }
            }
            if(currContentCategory.getIsParent()){
                deleteChildrenById(id, date);
            }
            return 1;
        }
        throw new DaoException("delete category - failed");
    }

    /**
     * recursively delete the children's content
     * @param id
     * @param date
     * @throws DaoException
     */
    private void deleteChildrenById(Long id, Date date) throws DaoException{
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        for(TbContentCategory category:list){
            TbContentCategory updateCategory = new TbContentCategory();
            updateCategory.setId(category.getId());
            updateCategory.setStatus(2);
            updateCategory.setUpdated(date);
            int index = tbContentCategoryMapper.updateByPrimaryKeySelective(updateCategory);
            if(index == 1){
                if(category.getIsParent()){
                    deleteChildrenById(category.getId(), date);
                }
            } else {
                throw new DaoException("delete category - update category failed");
            }
        }
    }

}
