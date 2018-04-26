package code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchPanel {

    public void retrieveInfos(List<SDirectory> dirs , String dirName, int depth) throws IOException {

        /**
         * 使用此函数获得指定目录下的信息并显示s。
         */
        //获取pathName的File对象

        File dirFile = new File(dirName);
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists()) {
            System.out.println("Do not exit");
            return;
        }
        //判断如果不是一个目录，就判断是不是一个文件，是文件则输出文件路径
        if (!dirFile.isDirectory()) {
            if (dirFile.isFile()) {
                SFile sfile = new SFile(dirFile);
                dirs.add((SDirectory) sfile);
            }
            return;
        }

        SDirectory directory = new SDirectory(dirFile);
        dirs.add(directory);

        List<SFile> files = new ArrayList<>();
        FileListManager.addFileList(directory.getFilePath(),files);

        //获取此目录下的所有文件名与目录名
        String[] fileList = dirFile.list();
        int currentDepth=depth+1;
        if (fileList!=null){

            for (int i = 0; i < fileList.length; i++) {
                //遍历文件目录
                String item = fileList[i];
                File file = new File(dirFile.getPath(),item);
                String name = file.getName();
                //如果是一个目录，搜索深度depth++，输出目录名后，进行递归
                if (file.isDirectory()) {
                    //递归
                    retrieveInfos(dirs,file.getCanonicalPath(),currentDepth);
                }else{

                    //使用文件类保存信息
                    SFile sfile = new SFile(file);
                    FileListManager.updateFileList(directory.getFilePath(),sfile);

                    //计算目录类的占空间大小
                    long size = 0;

                    for (SFile f: FileListManager.getFileList(directory.getFilePath())){
                        size = size +f.getFileSize();
                    }
                    directory.setFileSize(size);
                    directory.setFileSizeStr(directory.getPrintSize(size));

                }

            }
        }

    }

}
