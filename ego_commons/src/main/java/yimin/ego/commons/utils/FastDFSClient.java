package yimin.ego.commons.utils;

import com.sun.org.glassfish.gmbal.NameValue;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.*;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/2 16:06
 *   @Description :
 *
 */
public class FastDFSClient {

    // Use relative path
    private static final String CONF_FILENAME = "fdfs_client.conf";
    private static StorageClient storageClient = null;

    // only onload one time
    static {
        try {
            ClientGlobal.init(CONF_FILENAME);
            TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param inputStream
     *      upload the file's inputStream
     * @param fileName
     *      the original name of the file
     * @return
     */
    public static String[] uploadFile(InputStream inputStream, String fileName){
        try {
            // metadata of file
            NameValuePair[] meta_list = new NameValuePair[2];
            // original name
            meta_list[0] = new NameValuePair("file name", fileName);
            meta_list[1] = new NameValuePair("file length", inputStream.available() + " ");
            byte[] file_buff = null;
            if(inputStream != null){
                // the length of the file
                int len = inputStream.available();
                file_buff = new byte[len];
                // read the file to the buffer
                inputStream.read(file_buff);
            }
            // upload the file (parsing by byte array)
            String[] fields = storageClient.upload_file(file_buff, getFileExt(fileName), meta_list);
            return fields;
        } catch (Exception ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param file
     * @param fileName
     * @return return null if fail
     */
    public static String[] uploadFile(File file, String fileName){
        FileInputStream fis = null;
        try{
            NameValuePair[] meta_list = null;
            fis = new FileInputStream(file);
            byte[] file_buff = null;
            if(fis != null){
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            String[] fileids = storageClient.upload_file(file_buff, getFileExt(fileName), meta_list);
            return fileids;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(fis != null){
                try{
                    fis.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    /**
     * delete the file based on the groupName and fileName
     * @param groupName  default: group1
     * @param remoteFileName
     *  e.g. M00/00/00/wKgxgk5HbLvfP86RAAAAChd9X1Y736.jpg
     * @return 0: success  non-0: fail
     */
    public static int deleteFile(String groupName, String remoteFileName){
        try{
            int result = storageClient.delete_file(groupName == null ? "group1":groupName, remoteFileName);
            return result;
        } catch (Exception exception) {
//            exception.printStackTrace();
            return 0;
        }
    }

    /**
     * update a file that already exist
     * @param oldGroupName
     *  旧的组名
     *  @param oldFileName
     *  旧的文件名
     * @param file
     *  新文件
     * @param fileName
     *  新文件名
     * @return 返回空则为失败
     */
    public static String[] modifyFile(String oldGroupName, String oldFileName, File file, String fileName){
        String[] fields = null;
        try{
            // step 1: upload first
            fields = uploadFile(file, fileName);
            if(fields == null){
                return null;
            }
            // step 2: then delete the item
            int delResult = deleteFile(oldGroupName, oldFileName);
            if(delResult != 0){
                return null;
            }
        } catch (Exception e){
            return null;
        }
        return fields;
    }

    /**
     * download file
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downloadFile(String groupName, String remoteFileName){
        try{
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        } catch (Exception e){
            return null;
        }
    }

    public static NameValuePair[] getMetaData(String groupName, String remoteFileName){
        try{
            NameValuePair[] nvp = storageClient.get_metadata(groupName, remoteFileName);
            return nvp;
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * get the suffix type of the file
     * @return "jpg" or "".
     */
    private static String getFileExt(String fileName){
        if(StringUtils.isBlank(fileName) || !fileName.contains(".")){
            return "";
        } else {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
    }

}
