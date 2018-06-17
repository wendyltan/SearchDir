package modeFactory;

import builderModel.*;
import java.io.*;
import java.util.List;

public class LogMode implements Mode {

    public void saveLogFile(String dirName, List<SDirectory> dirs, int logType) throws IOException {
        System.out.println("Current directory:"+dirName);
        File file=null;
        if (logType==ModeFactory.ORI_LOG){
            file  = new File("ori.txt");
        }else if (logType==ModeFactory.NEW_LOG){
            file = new File("new.txt");
        }

        StringBuilder contentBuilder = new StringBuilder();
        for (SDirectory directory : dirs){
            List<SFile> tempList = FileListManager.getInstance().getFileList(directory.getFilePath());
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

    @Override
    public void printLog(BufferedInputStream inputStream) {

        try{
            int bytesRead = 0;
            byte[] buff = new byte[4096];
            while ((bytesRead = inputStream.read(buff)) != -1 ) {
                // 显示行号
                String chunk = new String(buff, 0, bytesRead);
                System.out.println(chunk);
            }
            inputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Log print success!");
    }
}
