import model.SFile;

import java.util.HashMap;
import java.util.List;

public class FileListManager {
    /**
     * 用于存储一个目录下的文件对象列表
     */


    /**
     * 使用单例模式
     */
    private static FileListManager manager = new FileListManager();

    private FileListManager(){

    }

    public static FileListManager getInstance(){
        return manager;
    }

    public void addFileList(String path,List<SFile> fileList){
        dirFiles.put(path,fileList);
    }

    /**
     * 获取当前目录对象存储的文件列表
     * @return
     */

    public List<SFile> getFileList(String path){
        return dirFiles.get(path);
    }

    public void updateFileList(String path,SFile file){
        if (dirFiles.containsKey(path)){
            List<SFile> files = dirFiles.get(path);
            files.add(file);
            dirFiles.put(path,files);
        }
    }

    private static HashMap<String,List<SFile>> dirFiles=new HashMap<String,List<SFile>>();



}
