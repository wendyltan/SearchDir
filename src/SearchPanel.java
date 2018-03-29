import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchPanel {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a file path below");
        String path = scanner.next();
        SearchPanel panel = new SearchPanel();
        ArrayList<SFile> files = new ArrayList<>();

        //dir printing and retrive filelist
        if (!path.isEmpty()){
            try {
                panel.showDir(files,path,0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //log saving
        Logger logger = new Logger();
        try {
            logger.saveLogFile(path,files);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showDir(ArrayList<SFile> files ,String dirName, int depth) throws IOException {


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
                files.add(sfile);
                System.out.println(sfile.getFilePath());
            }
            return;
        }


        for (int j = 0; j < depth; j++) {
            System.out.print("  ");
        }
        System.out.print("|--");
        System.out.println(dirFile.getName());
        SDirectory directory = new SDirectory(dirFile);
        files.add(directory);


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
                    showDir(files,file.getCanonicalPath(),currentDepth);
                }else{
                    //如果是文件，则直接输出文件名
                    for (int j = 0; j < currentDepth; j++) {
                        System.out.print("   ");
                    }
                    System.out.print("|--");
                    System.out.println(name);

                    //使用文件类保存信息
                    SFile sfile = new SFile(file);
                    files.add(sfile);

                }
            }
        }

    }

}
