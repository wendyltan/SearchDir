import java.io.*;
import java.util.List;
import java.util.Scanner;

public class CompareMode extends Mode {

    private static final int NEW_BIGGER = 0;
    private static final int OLD_BIGGER = 1;


    public static void writeDifference(String searchText, BufferedReader comparedReader,BufferedWriter rbw,int type)
            throws IOException {
        String lineStr = "";
        if (searchText == null) {
            return;
        }
        if ("".equals(searchText)) {
            rbw.write(searchText);
            return;
        }
        String lineText;
        //同一行与另一个文件里所有行比较
        while ((lineText = comparedReader.readLine()) != null) {
            String comparedLine = lineText.trim();
            if (searchText.equals(comparedLine)) {
                lineStr = "Old line: "+" "+searchText + "=###\n";
                break;
            }
            if (type==NEW_BIGGER){
                lineStr = "+\t" +" "+searchText+ "=#ADD IN NEW#\n";
            }else if(type==OLD_BIGGER){
                lineStr = "-\t" +" "+searchText+ "=#DELETE IN ORIGINAL#\n";
            }

        }
        rbw.write(lineStr);
        comparedReader.reset();

    }


    public void compareLogFile(List<SDirectory> dirs) throws IOException {
        //读取输入路径的目录与之前存起来的目录log进行比较
        File file = new File("ori.txt");
        File comFile = new File("new.txt");
        //如果不是同一个目录则无法比较
        if (!file.exists()){
            System.out.println("The original log file doesn't exist!");
        }else{
            //将第二次的保存到一个log文件
            StringBuilder contentBuilder = new StringBuilder();
            for (SDirectory directory : dirs){
                List<SFile> tempList = directory.getManager().getFileList();
                contentBuilder.append(directory.getFileInfo()+'\n');
                for (SFile sfile : tempList){
                    contentBuilder.append("----\t"+sfile.getFileInfo()+'\n');
                }

            }
            comFile.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(comFile));
            out.write(contentBuilder.toString());
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件


            //比较两个logfile
            System.out.println("======Start Compare!=======");
            long startTime = System.currentTimeMillis();
            BufferedReader oldReader=null;
            BufferedReader newReader=null;
            BufferedWriter rbw=null;

            try{
                oldReader = new BufferedReader(new FileReader(file));
                newReader = new BufferedReader(new FileReader(comFile));
                newReader.mark(90000000);
                oldReader.mark(90000000);
                rbw = new BufferedWriter(new FileWriter("differ.txt"));
                String lineText;
                if (file.length()<comFile.length()){
                    //compare start from new files
                    while ((lineText = newReader.readLine()) != null) {
                        String searchText = lineText.trim();
                        //new file bigger
                        writeDifference(searchText,oldReader,rbw,NEW_BIGGER);
                    }
                }else if(file.length()>comFile.length()){
                    //compare start with original file
                    while ((lineText = oldReader.readLine()) != null) {
                        String searchText = lineText.trim();
                        //old file bigger
                        writeDifference(searchText,newReader,rbw,OLD_BIGGER);
                    }
                }else{
                    System.out.println("File size the same,I don't think we need to compare!");
                }

                long endTime = System.currentTimeMillis();
                System.out.println("======Compare Over!=======");
                System.out.println("Time Spending:" + ((endTime - startTime) / 1000D) + "s");
            } catch (IOException e){
                e.printStackTrace();
            }finally {
                if (oldReader != null) {
                    try {
                        oldReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (newReader != null && rbw != null) {
                            try {
                                newReader.close();
                                rbw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }
}
