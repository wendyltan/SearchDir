package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileListManager {
    /**
     * 用于存储一个目录下的文件对象列表
     */


    public static void addFileList(String path,List<SFile> fileList){
        dirFiles.put(path,fileList);
    }

    /**
     * 获取当前目录对象存储的文件列表
     * @return
     */

    public static List<SFile> getFileList(String path){
        return dirFiles.get(path);
    }

    public static void updateFileList(String path,SFile file){
        if (dirFiles.containsKey(path)){
            List<SFile> files = dirFiles.get(path);
            files.add(file);
            dirFiles.put(path,files);
        }
    }

    public static HashMap<String,List<SFile>> dirFiles=new HashMap<String,List<SFile>>();



}
