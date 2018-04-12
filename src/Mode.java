import java.io.*;

public class Mode {

    private BufferedReader reader=null;

    public void getLogData(){
        System.out.println("Reading from logfile:");
        File file = new File("logfile.txt");
        try {
            reader = new BufferedReader(new FileReader(file));
            System.out.println("Log file read success!");
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

    }

    public void printLog(){
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
