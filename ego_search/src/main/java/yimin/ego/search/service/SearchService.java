package yimin.ego.search.service;

import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/5 19:00
 *   @Description :
 *
 */
public interface SearchService {

    /**
     * implement the search from solr
     * @param q
     * @param page
     * @param size
     * @return
     */
    Map<String, Object> search(String q, int page, int size);

    /**
     * add data to document of solr
     * @param ids
     * @return
     */
    int insert(long[] ids);

    int delete(String[] ids);
}
