package yimin.ego.commons.pojo;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/9/30 20:44
 *   @Description :
 *
 */
public class EgoResult {

    private int status;

    private Object data;

    private String msg;

    public static EgoResult ok(){
        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setMsg("ok");
        return er;
    }
    public static EgoResult ok(Object data){
        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setMsg("ok");
        er.setData(data);
        return er;
    }
    public static EgoResult ok(String msg){
        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setMsg(msg);
        return er;
    }
    public static EgoResult error(String msg){
        EgoResult er = new EgoResult();
        er.setStatus(400);
        er.setMsg(msg);
        return er;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
