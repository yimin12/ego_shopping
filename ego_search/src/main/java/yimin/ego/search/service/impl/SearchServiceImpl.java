package yimin.ego.search.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.solr.client.solrj.response.UpdateResponse;
import yimin.ego.dubbo.service.TbItemCatDubboService;
import yimin.ego.dubbo.service.TbItemDescDubboService;
import yimin.ego.dubbo.service.TbItemDubboService;
import yimin.ego.pojo.TbItem;
import yimin.ego.pojo.TbItemCat;
import yimin.ego.pojo.TbItemDesc;
import yimin.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;
import yimin.ego.search.pojo.SearchPojo;

import java.util.*;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/5 19:02
 *   @Description :
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrTemplate solrOperations;
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Override
    public Map<String, Object> search(String q, int page, int size) {
        Criteria c = new Criteria("item_keywords");
        c.is(q);

        // visualization in the query left panel
        HighlightQuery query = new SimpleHighlightQuery(c); // generate a query
        query.addSort(Sort.by(Sort.Direction.DESC,"__version__")); // sort in desc order
        query.setOffset((long)size*(page-1)); // set the property of paging
        query.setRows(size);

        // set highlight
        HighlightOptions hlOptions = new HighlightOptions();
        hlOptions.setSimplePrefix("<span stype='color:red'>");
        hlOptions.setSimplePostfix("</span>");
        hlOptions.addField("item_title item_sell_point");
        query.setHighlightOptions(hlOptions);

        // calculate total value when you search in visualization
        HighlightPage<SearchPojo> hlPage = solrOperations.queryForHighlightPage("ego", query, SearchPojo.class);
        List<HighlightEntry<SearchPojo>> highlighted = hlPage.getHighlighted();
        List<SearchPojo> listResult = new ArrayList<>();
        for(HighlightEntry<SearchPojo> hlEntity : highlighted){
            // non-highlighted data, can be regarded as docs in visualization
            SearchPojo searchPojo = hlEntity.getEntity();
            String image = searchPojo.getImage();
            searchPojo.setImages(image != null && !image.equals("") ? image.split(",") : new String[1]);
            // highlighted data
            List<HighlightEntry.Highlight> listHL = hlEntity.getHighlights();
            for(HighlightEntry.Highlight highlight : listHL){
                if(highlight.getField().getName().equals("item_title")){
                    searchPojo.setTitle(highlight.getSnipplets().get(0));
                }
            }
            listResult.add(searchPojo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("itemList", listResult);
        map.put("query", q);
        map.put("totalPages", hlPage.getTotalPages());
        map.put("page",page);
        return map;

    }

    @Override
    public int insert(long[] ids) {
        List<SearchPojo> list = new ArrayList<>();
        for(Long id : ids){
            SearchPojo sp = new SearchPojo();
            TbItem tbItem = tbItemDubboService.selectById(id);
            sp.setImage(tbItem.getImage());
            sp.setTitle(tbItem.getTitle());
            sp.setId(id);
            sp.setPrice(tbItem.getPrice());
            sp.setSellPoint(tbItem.getSellPoint());
            TbItemCat tbItemCat = tbItemCatDubboService.selectById(tbItem.getCid());
            sp.setCatName(tbItemCat.getName());
            TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(id);
            sp.setDesc(tbItemDesc.getItemDesc());
            list.add(sp);
        }
        UpdateResponse response = solrOperations.saveBeans("ego", list);
        solrOperations.commit("ego");
        if(response.getStatus() == 0){
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(String[] ids) {
        List<String> list = Arrays.asList(ids);
        UpdateResponse response = solrOperations.deleteByIds("ego", list);
        solrOperations.commit("ego");
        if(response.getStatus() == 0){
            return 1;
        }
        return 0;
    }
}
