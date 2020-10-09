package yimin.ego.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 22:23
 *   @Description :
 *
 */
public class JsonUtils {

    // define the jackson object
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Transfer to json from object
     * <p>Title: pojoToJson</p>
     * <p>Description </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data){
        try{
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Transfer to object from json
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType){
        try{
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Transfer to list of pojo from json
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType){
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try{
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Transfer to Map from json
     * @param jsonData
     * @param keyType
     * @param valueType
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> Map<K, V> jsonToMap(String jsonData, Class<K> keyType, Class<V> valueType){
        JavaType javaType = MAPPER.getTypeFactory().constructMapType(Map.class, keyType, valueType);
        try{
            Map<K, V> map = MAPPER.readValue(jsonData, javaType);
            return map;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

