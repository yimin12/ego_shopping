package yimin.ego.dubbo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import yimin.ego.commons.exception.DaoException;
import yimin.ego.dubbo.service.TbItemParamDubboService;
import yimin.ego.mapper.TbItemParamMapper;
import yimin.ego.pojo.TbItemParam;
import yimin.ego.pojo.TbItemParamExample;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 21:30
 *   @Description :
 *
 */
@Service
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public List<TbItemParam> selectByPage(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(null);
        PageInfo<TbItemParam> pi = new PageInfo<>();
        return pi.getList();
    }

    @Override
    public long selectCount() {
        return tbItemParamMapper.countByExample(null);
    }

    @Override
    public TbItemParam selectByCatId(Long catId) {
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(catId);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if(list != null && list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insert(TbItemParam tbItemParam) {
        return tbItemParamMapper.insert(tbItemParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(long[] ids) throws DaoException {
        int index = 0 ;
        for(long id :ids){
            index+=tbItemParamMapper.deleteByPrimaryKey(id);
        }
        if(ids.length==index){
            return 1;
        }
        throw new DaoException("Delete failed");
    }
}
