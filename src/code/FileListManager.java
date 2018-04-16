package code;

import java.util.ArrayList;
import java.util.List;

public class FileListManager {
    /**
     * 用于存储一个目录下的文件对象列表
     */

    private List<SFile> mFileList = new ArrayList<>();

    /**
     * 获取当前目录对象存储的文件列表
     * @return
     */

    public List<SFile> getFileList(){
        return mFileList;
    }


}
