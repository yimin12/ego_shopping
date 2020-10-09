package yimin.ego.commons.utils;

import java.util.Random;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 18:28
 *   @Description :
 *
 */
public class IDUtils {

    /**
     *  generate the name for the picture
     */
    public static String genImageName(){
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int end3 = random.nextInt(999);
        String str = millis + String.format("%03d", end3);
        return str;
    }

    /**
     *  generate id for items
     */
    public static long genItemId(){
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int end2 = random.nextInt(99);
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }

//    public static void main(String[] args) {
//        for(int i = 0; i < 100; i++){
//            System.out.println(genItemId());
//        }
//    }
}
