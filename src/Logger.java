import java.io.*;
import java.util.ArrayList;

public class Logger {

    public void getLog(){

    }

    public void saveLogFile(String dirName, ArrayList<SFile> files) throws IOException {
        System.out.println("Current directory:"+dirName);
        File file  = new File(dirName+"\\logfile.txt");
        StringBuilder contentBuilder = new StringBuilder();
        for (SFile sfile:files){
            contentBuilder.append(sfile.getFileInfo());
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
