package code;

import java.io.*;
import java.util.List;

public class LogMode extends ModeFactory {

    public void saveLogFile(String dirName, List<SDirectory> dirs) throws IOException {
        System.out.println("Current directory:"+dirName);
        File file  = new File("ori.txt");
        StringBuilder contentBuilder = new StringBuilder();
        for (SDirectory directory : dirs){
            List<SFile> tempList = FileListManager.getFileList(directory.getFilePath());
            contentBuilder.append(directory.getFileInfo()+'\n');
            for (SFile sfile : tempList){
                contentBuilder.append("----\t"+sfile.getFileInfo()+'\n');
            }

        }
        file.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(contentBuilder.toString());
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
        System.out.println("File write success!");

    }

}
