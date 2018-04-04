import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logger {

    public void getLog(){

    }

    public void saveLogFile(String dirName, List<SDirectory> dirs) throws IOException {
        System.out.println("Current directory:"+dirName);
        File file  = new File("logfile.txt");
        StringBuilder contentBuilder = new StringBuilder();
        for (SDirectory directory : dirs){
            List<SFile> tempList = directory.getManager().getFileList();
            contentBuilder.append("----\t"+directory.getFileInfo()+'\n');
            for (SFile sfile : tempList){
                contentBuilder.append(sfile.getFileInfo()+'\n');
            }

        }
        file.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(contentBuilder.toString());
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
        System.out.println("File write success!");

    }

    public void compareLogFile(String dirName,Comparer comparer){

    }
}
