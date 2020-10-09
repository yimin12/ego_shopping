package yimin.ego.commons.pojo;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/4 22:10
 *   @Description :
 *      Advertisement from big data group
 */
public class BigAd {

    // ad backup
    private String srcB;
    // display the height of ad
    private int height;
    // info when mouse mounting above ad
    private String alt;
    // the width of ad
    private int width;
    // url of the ad
    private String src;
    // width of ad backup
    private int widthB;
    // redirect url
    private String href;
    // height of ad backup
    private int heightB;

    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getWidthB() {
        return widthB;
    }

    public void setWidthB(int widthB) {
        this.widthB = widthB;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getHeightB() {
        return heightB;
    }

    public void setHeightB(int heightB) {
        this.heightB = heightB;
    }
}
