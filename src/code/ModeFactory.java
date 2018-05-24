package code;
import java.io.*;

public abstract class ModeFactory {

    public static final int ORI_LOG = 0;
    public static final int NEW_LOG = 1;

    public BufferedInputStream getLogData(int log_type){
        BufferedInputStream inputStream = null;
        System.out.println("Reading from log file:");
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
            inputStream = new BufferedInputStream(new FileInputStream(file));
            System.out.println("Log file read success!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

        return inputStream;

    }

    public void printLog(BufferedInputStream inputStream){

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


    public static ModeFactory getMode(String mode){
        if (mode=="Log"){
            return new LogMode();
        }else if(mode =="Compare"){
            return new CompareMode();
        }else{
            System.out.println("Please enter right mode name!");
        }
        return null;
    }
}
