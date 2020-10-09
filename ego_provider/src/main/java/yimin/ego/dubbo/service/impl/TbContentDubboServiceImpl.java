package yimin.ego.dubbo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import yimin.ego.commons.exception.DaoException;
import yimin.ego.dubbo.service.TbContentDubboService;
import yimin.ego.mapper.TbContentMapper;
import yimin.ego.pojo.TbContent;
import yimin.ego.pojo.TbContentExample;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/3 13:32
 *   @Description :
 *
 */
@Service
public class TbContentDubboServiceImpl implements TbContentDubboService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public List<TbContent> selectByPage(Long categoryId, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        TbContentExample example = new TbContentExample();
        if(categoryId != 0){
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> list = tbContentMapper.selectByExample(example);
        PageInfo<TbContent> pi = new PageInfo<>(list);
        return pi.getList();
    }

    @Override
    public long selectCountByCategoryId(Long categoryId) {
        TbContentExample example = new TbContentExample();
        if(categoryId != 0){
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        return tbContentMapper.countByExample(example);
    }

    @Override
    public int insert(TbContent tbContent) {
        return tbContentMapper.insert(tbContent);
    }

    @Override
    public int update(TbContent tbContent) {
        return tbContentMapper.updateByPrimaryKeySelective(tbContent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByIds(long[] ids) throws DaoException {
        int index = 0;
        for(long id : ids){
            index += tbContentMapper.deleteByPrimaryKey(id);
        }
        if(index == ids.length){
            return 1;
        }
        throw new DaoException("delete failed");
    }

    @Override
    public List<TbContent> selectAllByCategoryId(Long categoryId) {
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        example.setOrderByClause("updated desc");
        return tbContentMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public TbContent selectById(Long id) {
        return tbContentMapper.selectByPrimaryKey(id);
    }
}
