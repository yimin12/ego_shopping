package yimin.ego.search.pojo;

import org.apache.solr.client.solrj.beans.Field;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/5 18:55
 *   @Description :
 *      This is used for jsp and will be displayed in web page
 */
public class SearchPojo {

    // @Field guarantees that this field will match the corresponding field in solr
    @Field("id")
    private Long id;
    private String[] images;
    @Field("item_image")
    private String image;
    @Field("item_sell_point")
    private String sellPoint;
    @Field("item_price")
    private Long price;
    @Field("item_title")
    private String title;
    @Field("item_desc")
    private String desc;
    @Field("item_category_name")
    private String catName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
