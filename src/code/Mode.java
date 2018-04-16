package code;

import java.io.*;

public class Mode {
    public static final int ORI_LOG = 0;
    public static final int NEW_LOG = 1;


    public BufferedReader getLogData(int log_type){
        BufferedReader reader = null;
        System.out.println("Reading from logfile:");
        File file;
        switch (log_type){
            case ORI_LOG:
                file = new File("ori.txt");
                break;
            case NEW_LOG:
                file = new File("new.txt");
                break;
            default:
                System.out.println("Invalid type,showing default old log:...");
                file = new File("ori.txt");
                break;
        }

        try {
            reader = new BufferedReader(new FileReader(file));
            System.out.println("Log file read success!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

        return reader;

    }

    public void printLog(BufferedReader reader){
        try{
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println(line + ": " + tempString);
                line++;
            }

            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Log print success!");
    }
}
